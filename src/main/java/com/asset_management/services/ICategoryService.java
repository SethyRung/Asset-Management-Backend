package com.asset_management.services;

import com.asset_management.dto.Category.CategoryReqDTO;
import com.asset_management.dto.Category.CategoryResDTO;
import com.asset_management.utils.PaginationPage;

public interface ICategoryService {
    public CategoryResDTO addCategory(CategoryReqDTO categoryReqDTO);
    public PaginationPage<CategoryResDTO> getAllCategory(String search, int page, int size);
    public CategoryResDTO getCategoryById(Long id);
    public CategoryResDTO updateCategory(Long id, CategoryReqDTO categoryReqDTO);
    public void deleteCategory(Long id);
}
