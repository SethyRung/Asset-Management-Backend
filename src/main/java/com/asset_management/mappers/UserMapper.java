package com.asset_management.mappers;

import com.asset_management.dto.Assets.AssetsResDTO;
import com.asset_management.dto.User.UserResDTO;
import com.asset_management.models.Asset;
import com.asset_management.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResDTO toDTO(User user);
}

