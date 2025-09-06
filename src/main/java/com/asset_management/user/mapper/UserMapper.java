package com.asset_management.user.mapper;

import com.asset_management.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    com.asset_management.user.dto.UserResDTO toDTO(User user);
}

