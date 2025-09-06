package com.asset_management.auth.service;

import com.asset_management.auth.dto.AuthResponseDTO;
import com.asset_management.auth.dto.LoginRequestDTO;
import com.asset_management.auth.dto.RegisterRequestDTO;
import com.asset_management.auth.dto.ResetPasswordDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface IAuthService {
    public void register(RegisterRequestDTO registerRequestDTO);
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO);
    public AuthResponseDTO refreshToken(HttpServletRequest request);
    public void getResetPasswordEmail(String usernameOrEmail);
    public void resetPassword(ResetPasswordDTO resetPasswordDTO);
}
