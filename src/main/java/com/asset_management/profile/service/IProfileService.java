package com.asset_management.profile.service;

import com.asset_management.profile.dto.ChangePasswordDTO;
import com.asset_management.profile.dto.ProfileReqDTO;
import com.asset_management.user.dto.UserResDTO;

public interface IProfileService {
    public UserResDTO getProfile();
    public UserResDTO updateProfile(ProfileReqDTO profileReqDTO);
    public void changePassword(ChangePasswordDTO changePassword);
}
