package com.asset_management.services;

import com.asset_management.dto.Profile.ChangePasswordDTO;
import com.asset_management.dto.Profile.ProfileReqDTO;
import com.asset_management.dto.User.UserResDTO;
import com.asset_management.models.User;

public interface IProfileService {
    public UserResDTO getProfile();
    public UserResDTO updateProfile(ProfileReqDTO profileReqDTO);
    public void changePassword(ChangePasswordDTO changePassword);
}
