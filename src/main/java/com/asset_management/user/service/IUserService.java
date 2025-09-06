package com.asset_management.user.service;

import com.asset_management.user.dto.UserReqDTO;
import com.asset_management.user.dto.UserResDTO;
import com.asset_management.user.model.User;
import com.asset_management.utils.PaginationPage;


public interface IUserService {
    public PaginationPage<com.asset_management.user.dto.UserResDTO> getUserList(String search, int page, int size);

    public UserResDTO addUser(UserReqDTO userReqDTO);
    public UserResDTO getUserById(Long id);
    public UserResDTO updateUser(Long id, UserReqDTO userReqDTO);
    public void deleteUser(Long id);

    public User getCurrentUser();
}
