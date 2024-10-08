package com.asset_management.services.impl;

import com.asset_management.dto.User.ChangePasswordDTO;
import com.asset_management.dto.User.UserResponseDTO;
import com.asset_management.enums.HttpStatusEnum;
import com.asset_management.enums.RoleEnum;
import com.asset_management.exceptions.ErrorException;
import com.asset_management.models.User;
import com.asset_management.repositories.UserRepository;
import com.asset_management.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDTO> getUserList(RoleEnum role) {
        List<User> userList = userRepository.findAllByRole(role != null ? role.name() : "");
        return userList.stream().map(u -> new UserResponseDTO(u.getId(),u.getFirstName(),u.getLastName(),u.getUsername(), u.getEmail(), u.getJoinDate(), u.getStatus(), u.getRole().name())).toList();
    }

    @Override
    public void changePassword(User user, ChangePasswordDTO changePassword) {
        if(!passwordEncoder.matches(changePassword.getCurrentPassword(), user.getPassword()))
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "Current password is incorrect.");
        if(!changePassword.getNewPassword().equals(changePassword.getConfirmPassword()))
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "The confirm password doesn’t match.");
        if(passwordEncoder.matches(changePassword.getNewPassword(), user.getPassword()))
            throw new ErrorException(HttpStatusEnum.BAD_REQUEST, "This password has already been used. Please choose a different one.");
        user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        userRepository.save(user);
    }
}
