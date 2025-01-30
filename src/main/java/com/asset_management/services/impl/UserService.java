package com.asset_management.services.impl;

import com.asset_management.dto.User.ChangePasswordDTO;
import com.asset_management.dto.User.UserReqDTO;
import com.asset_management.dto.User.UserResDTO;
import com.asset_management.enums.HttpStatusEnum;
import com.asset_management.exceptions.ErrorException;
import com.asset_management.mappers.UserMapper;
import com.asset_management.models.User;
import com.asset_management.repositories.UserRepository;
import com.asset_management.services.EmailService;
import com.asset_management.services.IUserService;
import com.asset_management.utils.PaginationPage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final EmailService emailService;

    @Value("${application.client-url}")
    private String clientUrl;
    @Value("${application.smtp.domain}")
    private String emailDomain;

    @Override
    public PaginationPage<UserResDTO> getUserList(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> resultPage;
        if (search == null || search.isBlank()) {
            resultPage = userRepository.findAll(pageable);
        } else {
            resultPage = userRepository.findByUsernameContainingIgnoreCase(search, pageable);
        }

        return new PaginationPage<>(resultPage.map(userMapper::toDTO));
    }

    @Override
    public void changePassword(User user, ChangePasswordDTO changePassword) {
        if (!passwordEncoder.matches(changePassword.getCurrentPassword(), user.getPassword()))
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "Current password is incorrect.");
        if (!changePassword.getNewPassword().equals(changePassword.getConfirmPassword()))
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "The confirm password doesn't match.");
        if (passwordEncoder.matches(changePassword.getNewPassword(), user.getPassword()))
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "This password has already been used. Please choose a different one.");
        user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserResDTO addUser(UserReqDTO userReqDTO) {
        User user = new User();
        String resetPasswordId = UUID.randomUUID().toString();
        String password = generatePassword(8);
        LocalDateTime currentDateTime = LocalDateTime.now();

        user.setPassword(passwordEncoder.encode(password));
        user.setResetPasswordId(resetPasswordId);
        user.setResetPasswordExpireIn(LocalDateTime.of(currentDateTime.getYear(),
                currentDateTime.getMonth(), currentDateTime.getDayOfMonth() + 1,
                currentDateTime.getHour(), currentDateTime.getMinute()));

        UserResDTO userResDTO = saveUser(user, userReqDTO);

        try {
            String resetPasswordUrl = clientUrl + "/reset-password?account=" + userResDTO.getUsername() + "&resetId=" + resetPasswordId;
            String from = "Noreply <" + emailDomain + ">";
            ClassPathResource resource = new ClassPathResource("templates/reset-default-password.html");
            String emailContent = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

            emailContent = emailContent.replace("{{firstName}}", userResDTO.getFirstName());
            emailContent = emailContent.replace("{{defaultPassword}}", password);
            emailContent = emailContent.replace("{{resetPasswordUrl}}", resetPasswordUrl);

            emailService.sendEmail(from, userResDTO.getEmail(), "Reset Your Account Password", emailContent);
        } catch (Exception e) {
            throw new ErrorException(HttpStatusEnum.INTERNAL_SERVER_ERROR, "Unable to send the OTP at this time. Please verify your email or try again later.");
        }

        return userResDTO;
    }

    @Override
    public UserResDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "User not found"));
        return userMapper.toDTO(user);
    }

    @Override
    public UserResDTO updateUser(Long id, UserReqDTO userReqDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "User not found"));
        return saveUser(user, userReqDTO);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "User not found"));
        user.setStatus(false);
        userRepository.save(user);
    }

    private UserResDTO saveUser(User user, UserReqDTO userReqDTO) {
        user.setFirstName(userReqDTO.getFirstName());
        user.setLastName(userReqDTO.getLastName());
        user.setUsername(userReqDTO.getUsername());
        user.setEmail(userReqDTO.getEmail());
        user.setJoinDate(LocalDate.now());
        user.setProfile(userReqDTO.getProfile());
        user.setStatus(true);
        user.setRole(userReqDTO.getRole());

        return userMapper.toDTO(userRepository.save(user));
    }

    private String generatePassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = secureRandom.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }

    public User getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(()->new ErrorException(HttpStatusEnum.BAD_REQUEST, "User not found with username: " + username));
    }
}
