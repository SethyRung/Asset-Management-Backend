package com.asset_management.services;

import com.asset_management.dto.Category.CategoryReqDTO;
import com.asset_management.models.Category;
import com.asset_management.utils.PaginationPage;

public interface ICategoryService {
    public Category addCategory(CategoryReqDTO categoryReqDTO);
    public PaginationPage<Category> getAllCategory(String search, int page, int size);
    public Category getCategoryById(Long id);
    public Category updateCategory(Long id, CategoryReqDTO categoryReqDTO);
    public void deleteCategory(Long id);
}
