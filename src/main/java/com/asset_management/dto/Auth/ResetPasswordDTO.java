package com.asset_management.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResetPasswordDTO {
    private String resetId;
    private String newPassword;
    private String confirmPassword;
}
