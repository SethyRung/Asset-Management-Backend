package com.asset_management.services.impl;

import com.asset_management.dto.Auth.LoginRequestDTO;
import com.asset_management.dto.Auth.AuthResponseDTO;
import com.asset_management.dto.Auth.RegisterRequestDTO;
import com.asset_management.dto.Auth.ResetPasswordDTO;
import com.asset_management.enums.HttpStatusEnum;
import com.asset_management.exceptions.ErrorException;
import com.asset_management.models.Token;
import com.asset_management.models.User;
import com.asset_management.repositories.TokenRepository;
import com.asset_management.repositories.UserRepository;
import com.asset_management.services.EmailService;
import com.asset_management.services.IAuthService;
import com.asset_management.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService, LogoutHandler {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${application.website-domain}")
    private String websiteDomain;
    @Value("${application.smtp.domain}")
    private String emailDomain;
    @Value("${application.security.jwt.refresh-token.secret-key}")
    private String refreshSecretKey;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        var user = User.builder()
                .firstName(registerRequestDTO.getFirstName())
                .lastName(registerRequestDTO.getLastName())
                .username(registerRequestDTO.getUsername())
                .email(registerRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .joinDate(LocalDate.now())
                .profile(registerRequestDTO.getProfile())
                .status(true)
                .role(registerRequestDTO.getRole())
                .build();
        var savedUser = userRepository.save(user);
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, accessToken);
        return new AuthResponseDTO(accessToken, refreshToken);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getEmail(),
                            loginRequestDTO.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "Email or Password is invalid.");
        }
        var user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Email or Password is invalid."));
        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new AuthResponseDTO(accessToken, refreshToken);
    }

    public AuthResponseDTO refreshToken(
            HttpServletRequest request
    ) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "Please provide all parameters.");

        refreshToken = authHeader.substring(7);
        try {
            final String userEmail = jwtService.extractEmail(refreshToken, refreshSecretKey);

            var user = userRepository.findByUsername(userEmail)
                    .orElseThrow();
            var accessToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, accessToken);
            return new AuthResponseDTO(accessToken, refreshToken);
        } catch (ExpiredJwtException e) {
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "Token is already expired");
        } catch (Exception e) {
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "Token is invalid");
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType("BEARER")
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "Something went wrong.");
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Something went wrong."));
        storedToken.setExpired(true);
        storedToken.setRevoked(true);
        tokenRepository.save(storedToken);
        SecurityContextHolder.clearContext();
    }

    @Override
    public void getResetPasswordEmail(String usernameOrEmail) {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail).orElseThrow(() -> new ErrorException(HttpStatusEnum.INTERNAL_SERVER_ERROR, "User does not exist."));

        try {
            String resetPasswordId = UUID.randomUUID().toString();
            LocalDateTime currentDateTime = LocalDateTime.now();
            String resetPasswordUrl = websiteDomain + "/forgot-password?account=" + usernameOrEmail + "&resetId=" + resetPasswordId;

            user.setResetPasswordId(resetPasswordId);
            user.setResetPasswordExpireIn(LocalDateTime.of(currentDateTime.getYear(), currentDateTime.getMonth(), currentDateTime.getDayOfMonth(), currentDateTime.getHour(), currentDateTime.getMinute() + 15));
            userRepository.save(user);

            String from = "Noreply <" + emailDomain + ">";
            String html = "<!DOCTYPE html>" +
                    "<html lang=\"en\">" +
                    "  <head>" +
                    "    <meta charset=\"UTF-8\" />" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />" +
                    "    <title>Password Reset</title>" +
                    "    <style>" +
                    "      body {" +
                    "        font-family: Arial, sans-serif;" +
                    "        background-color: #f4f4f4;" +
                    "        margin: 0;" +
                    "        padding: 20px;" +
                    "      }" +
                    "      .container {" +
                    "        max-width: 600px;" +
                    "        margin: 0 auto;" +
                    "        background-color: #fff;" +
                    "        padding: 20px;" +
                    "        border-radius: 8px;" +
                    "        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);" +
                    "      }" +
                    "      h1 {" +
                    "        font-size: 24px;" +
                    "        color: #333;" +
                    "      }" +
                    "      p {" +
                    "        font-size: 16px;" +
                    "        color: #555;" +
                    "      }" +
                    "      .button {" +
                    "        display: inline-block;" +
                    "        padding: 10px 20px;" +
                    "        background-color: #3f3f46;" +
                    "        color: #fff !important;" +
                    "        text-decoration: none;" +
                    "        border-radius: 5px;" +
                    "        font-size: 16px;" +
                    "      }" +
                    "      .footer {" +
                    "        margin-top: 20px;" +
                    "        color: #888;" +
                    "      }" +
                    "    </style>" +
                    "  </head>" +
                    "  <body>" +
                    "    <div class=\"container\">" +
                    "      <h1>Hi " + user.getFirstName() + ",</h1>" +
                    "      <p>You recently requested to reset the password for your account.</p>" +
                    "      <p>Click the button below to proceed.</p>" +
                    "      <p>" +
                    "        <a href=\"" + resetPasswordUrl + "\" class=\"button\">Reset Password</a>" +
                    "      </p>" +
                    "      <p>" +
                    "        If you did not request a password reset, please ignore this email. This" +
                    "        password reset link is only valid for the next 15 minutes." +
                    "      </p>" +
                    "      <p class=\"footer\">" +
                    "        Thanks,<br />" +
                    "        The SR team" +
                    "      </p>" +
                    "    </div>" +
                    "  </body>" +
                    "</html>";
            emailService.sendEmail(from, user.getEmail(), "Reset Your Account Password", html);
        } catch (Exception e) {
            throw new ErrorException(HttpStatusEnum.INTERNAL_SERVER_ERROR, "Unable to send the OTP at this time. Please verify your email or try again later.");
        }
    }

    @Override
    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User user = userRepository.findByResetPasswordId(resetPasswordDTO.getResetId()).orElseThrow(() -> new ErrorException(HttpStatusEnum.INTERNAL_SERVER_ERROR, "Reset ID does not exist."));
        if (!resetPasswordDTO.getNewPassword().equals(resetPasswordDTO.getConfirmPassword()))
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "The confirm password doesn’t match.");
        if (user.getResetPasswordExpireIn().isBefore(LocalDateTime.now()))
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "This link has expired.");
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        user.setResetPasswordId(null);
        user.setResetPasswordExpireIn(null);
        userRepository.save(user);
    }
}
