package com.asset_management.services.impl;

import com.asset_management.dto.Profile.ChangePasswordDTO;
import com.asset_management.dto.Profile.ProfileReqDTO;
import com.asset_management.dto.User.UserResDTO;
import com.asset_management.enums.HttpStatusEnum;
import com.asset_management.exceptions.ErrorException;
import com.asset_management.mappers.UserMapper;
import com.asset_management.models.User;
import com.asset_management.repositories.UserRepository;
import com.asset_management.services.IProfileService;
import com.asset_management.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService implements IProfileService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserService userService;
    private final UserMapper userMapper;

    @Override
    public UserResDTO getProfile() {
        return userMapper.toDTO(userService.getCurrentUser());
    }

    @Override
    public UserResDTO updateProfile(ProfileReqDTO profileReqDTO) {
        User user = userService.getCurrentUser();
        user.setFirstName(profileReqDTO.getFirstName());
        user.setLastName(profileReqDTO.getLastName());
        user.setUsername(profileReqDTO.getUsername());
        user.setProfile(profileReqDTO.getProfile());

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    public void changePassword(ChangePasswordDTO changePassword) {
        User user = userService.getCurrentUser();
        if (!passwordEncoder.matches(changePassword.getCurrentPassword(), user.getPassword()))
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "Current password is incorrect.");
        if (!changePassword.getNewPassword().equals(changePassword.getConfirmPassword()))
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "The confirm password doesn't match.");
        if (passwordEncoder.matches(changePassword.getNewPassword(), user.getPassword()))
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "This password has already been used. Please choose a different one.");
        user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        userRepository.save(user);
    }
}
