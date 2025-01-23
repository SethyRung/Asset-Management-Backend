package com.asset_management.mappers;

import com.asset_management.dto.Assets.AssetsResDTO;
import com.asset_management.models.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AssetMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "assignedTo.id", target = "assignedTo")
    @Mapping(source = "acquisitionDate", target = "acquisitionDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "warrantyExpiryDate", target = "warrantyExpiryDate", dateFormat = "yyyy-MM-dd")
    AssetsResDTO toDTO(Asset asset);
}

