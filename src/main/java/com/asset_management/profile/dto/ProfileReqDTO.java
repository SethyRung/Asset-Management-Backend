package com.asset_management.profile.dto;

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