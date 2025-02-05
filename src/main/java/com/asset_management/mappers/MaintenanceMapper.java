package com.asset_management.mappers;

import com.asset_management.dto.Maintenance.MaintenanceResDTO;
import com.asset_management.models.Maintenance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AssetMapper.class, UserMapper.class})

public interface MaintenanceMapper {


    @Mapping(source = "asset", target = "asset")
    @Mapping(source = "performedBy", target = "performedBy")
    MaintenanceResDTO toDTO(Maintenance assetHistory);
}

