package com.asset_management.assets.mapper;

import com.asset_management.assets.dto.AssetsResDTO;
import com.asset_management.assets.model.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AssetMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "assignedTo.id", target = "assignedTo")
    @Mapping(source = "acquisitionDate", target = "acquisitionDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "warrantyExpiryDate", target = "warrantyExpiryDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "documents", target = "documents", qualifiedByName = "stringTOStringList")
    AssetsResDTO toDTO(Asset asset);

    @Named("stringTOStringList")
    default List<String> stringTOStringList(String str) {
        return Arrays.asList(str.split(","));
    }
}

