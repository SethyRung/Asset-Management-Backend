package com.asset_management.mappers;

import com.asset_management.dto.AssetsHistory.AssetsHistoryResDTO;
import com.asset_management.models.AssetHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AssetMapper.class)

public interface AssetHistoryMapper {


    @Mapping(source = "asset", target = "asset")
    @Mapping(source = "actionPerformedBy.id", target = "userId")
    @Mapping(source = "actionDate", target = "actionDate", dateFormat = "yyyy-MM-dd")
    AssetsHistoryResDTO toDTO(AssetHistory assetHistory);
}

