package com.asset_management.maintenance.mapper;

import com.asset_management.assets.mapper.AssetMapper;
import com.asset_management.maintenance.dto.MaintenanceResDTO;
import com.asset_management.maintenance.model.Maintenance;
import com.asset_management.user.mapper.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AssetMapper.class, UserMapper.class})

public interface MaintenanceMapper {
    @Mapping(source = "asset", target = "asset")
    @Mapping(source = "performedBy", target = "performedBy")
    MaintenanceResDTO toDTO(Maintenance assetHistory);
}

