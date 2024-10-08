package com.asset_management.services;

import com.asset_management.dto.User.ChangePasswordDTO;
import com.asset_management.dto.Auth.ResetPasswordDTO;
import com.asset_management.dto.User.UserResponseDTO;
import com.asset_management.enums.RoleEnum;
import com.asset_management.models.User;

import java.util.List;

public interface IUserService {
    public List<UserResponseDTO> getUserList(RoleEnum role);
    public void changePassword(User user, ChangePasswordDTO changePassword);
}
