package com.asset_management.dto.User;

import com.asset_management.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String profile;
    private RoleEnum role;
}