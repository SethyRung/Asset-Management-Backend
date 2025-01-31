package com.asset_management.dto.Profile;

import com.asset_management.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileReqDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String profile;
}