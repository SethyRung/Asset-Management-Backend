package com.asset_management.category.mapper;

import com.asset_management.category.dto.CategoryResDTO;
import com.asset_management.category.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResDTO toDTO(Category category);
}

