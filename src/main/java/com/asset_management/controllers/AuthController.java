package com.asset_management.controllers;

import com.asset_management.dto.Auth.LoginRequestDTO;
import com.asset_management.dto.Auth.AuthResponseDTO;
import com.asset_management.dto.Auth.RegisterRequestDTO;
import com.asset_management.dto.Auth.ResetPasswordDTO;
import com.asset_management.enums.ResponseMessageEnum;
import com.asset_management.services.impl.AuthService;
import com.asset_management.utils.ResponseBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth")
@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseBody<?>> register(@RequestBody RegisterRequestDTO registerRequestDTO){
        authService.register(registerRequestDTO);
        return ResponseEntity.ok(new ResponseBody<>());
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseBody<AuthResponseDTO>> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(new ResponseBody<>(authService.login(loginRequestDTO)));
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<ResponseBody<AuthResponseDTO>> refresh(HttpServletRequest request){
        return  ResponseEntity.ok(new ResponseBody<>(authService.refreshToken(request)));
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<ResponseBody<?>> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        authService.logout(request, response, authentication);
        return ResponseEntity.ok(new ResponseBody<>(ResponseBody.ResponseStatus.builder().code(ResponseMessageEnum.OK.getCode()).build()));
    }

    @GetMapping(value = "/reset-password")
    public ResponseEntity<ResponseBody<?>> getResetPasswordEmail(@RequestParam(name = "usernameOrEmail") String usernameOrEmail){
        authService.getResetPasswordEmail(usernameOrEmail);
        return ResponseEntity.ok(new ResponseBody<>(ResponseBody.ResponseStatus.builder().code(ResponseMessageEnum.OK.getCode()).build()));
    }

    @PostMapping(value = "/reset-password")
    public ResponseEntity<ResponseBody<?>> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO){
        authService.resetPassword(resetPasswordDTO);
        return ResponseEntity.ok(new ResponseBody<>(ResponseBody.ResponseStatus.builder().code(ResponseMessageEnum.OK.getCode()).build()));
    }
}
