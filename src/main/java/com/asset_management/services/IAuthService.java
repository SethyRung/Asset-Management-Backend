package com.asset_management.services;

import com.asset_management.dto.Auth.LoginRequestDTO;
import com.asset_management.dto.Auth.AuthResponseDTO;
import com.asset_management.dto.Auth.RegisterRequestDTO;
import com.asset_management.dto.Auth.ResetPasswordDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthService {
    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO);
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO);
    public AuthResponseDTO refreshToken(HttpServletRequest request);
    public void getResetPasswordEmail(String usernameOrEmail);
    public void resetPassword(ResetPasswordDTO resetPasswordDTO);
}
