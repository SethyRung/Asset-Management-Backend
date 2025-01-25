package com.asset_management.services;

import com.asset_management.dto.User.ChangePasswordDTO;
import com.asset_management.dto.User.UserReqDTO;
import com.asset_management.dto.User.UserResDTO;
import com.asset_management.models.User;
import com.asset_management.utils.PaginationPage;


public interface IUserService {
    public PaginationPage<UserResDTO> getUserList(String search, int page, int size);
    public void changePassword(User user, ChangePasswordDTO changePassword);

    public UserResDTO addUser(UserReqDTO userReqDTO);
    public UserResDTO getUserById(Long id);
    public UserResDTO updateUser(Long id, UserReqDTO userReqDTO);
    public void deleteUser(Long id);

    public User getCurrentUser();
}
