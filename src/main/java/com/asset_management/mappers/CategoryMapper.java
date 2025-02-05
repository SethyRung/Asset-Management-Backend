package com.asset_management.mappers;

import com.asset_management.dto.Category.CategoryResDTO;
import com.asset_management.models.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResDTO toDTO(Category category);
}

