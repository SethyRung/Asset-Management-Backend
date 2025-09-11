package com.asset_management.assets_history.mapper;

import com.asset_management.assets.mapper.AssetMapper;
import com.asset_management.assets_history.dto.AssetsHistoryResDTO;
import com.asset_management.assets_history.model.AssetHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AssetMapper.class)

public interface AssetHistoryMapper {


    @Mapping(source = "asset", target = "asset")
    @Mapping(source = "actionPerformedBy.id", target = "userId")
    @Mapping(source = "actionDate", target = "actionDate", dateFormat = "yyyy-MM-dd")
    AssetsHistoryResDTO toDTO(AssetHistory assetHistory);
}

