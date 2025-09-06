package com.asset_management.category.service;

import com.asset_management.category.dto.CategoryReqDTO;
import com.asset_management.category.dto.CategoryResDTO;
import com.asset_management.utils.PaginationPage;

public interface ICategoryService {
    public CategoryResDTO addCategory(CategoryReqDTO categoryReqDTO);
    public PaginationPage<CategoryResDTO> getAllCategory(String search, int page, int size);
    public CategoryResDTO getCategoryById(Long id);
    public CategoryResDTO updateCategory(Long id, CategoryReqDTO categoryReqDTO);
    public void deleteCategory(Long id);
}
