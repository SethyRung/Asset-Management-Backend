package com.asset_management.services.impl;

import com.asset_management.dto.Assets.AssetsReqDTO;
import com.asset_management.dto.Category.CategoryReqDTO;
import com.asset_management.enums.HttpStatusEnum;
import com.asset_management.exceptions.ErrorException;
import com.asset_management.models.Asset;
import com.asset_management.models.Category;
import com.asset_management.models.User;
import com.asset_management.repositories.CategoryRepository;
import com.asset_management.services.ICategoryService;
import com.asset_management.utils.PaginationPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(CategoryReqDTO categoryReqDTO) {
        Category category = new Category();
        return saveCategory(category, categoryReqDTO);
    }

    @Override
    public PaginationPage<Category> getAllCategory(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> resultPage;

        if (search == null || search.isBlank()) {
            resultPage = categoryRepository.findAll(pageable);
        } else {
            resultPage = categoryRepository.findByNameContainingIgnoreCase(search, pageable);
        }

        return new PaginationPage<>(resultPage);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Category not found"));
    }

    @Override
    public Category updateCategory(Long id, CategoryReqDTO categoryReqDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Category not found"));
        return saveCategory(category, categoryReqDTO);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private Category saveCategory(Category category, CategoryReqDTO categoryReqDTO){
        category.setName(categoryReqDTO.getName());
        category.setDescription(categoryReqDTO.getDescription());
        return categoryRepository.save(category);
    }
}
