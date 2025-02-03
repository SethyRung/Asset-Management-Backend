package com.asset_management.services.impl;

import com.asset_management.dto.Category.CategoryReqDTO;
import com.asset_management.dto.Category.CategoryResDTO;
import com.asset_management.enums.HttpStatusEnum;
import com.asset_management.exceptions.ErrorException;
import com.asset_management.mappers.CategoryMapper;
import com.asset_management.models.Category;
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
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResDTO addCategory(CategoryReqDTO categoryReqDTO) {
        Category category = new Category();
        return saveCategory(category, categoryReqDTO);
    }

    @Override
    public PaginationPage<CategoryResDTO> getAllCategory(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> resultPage;

        if (search == null || search.isBlank()) {
            resultPage = categoryRepository.findAllNotDeleted(pageable);
        } else {
            resultPage = categoryRepository.findByNameContainingIgnoreCase(search, pageable);
        }

        return new PaginationPage<>(resultPage.map(categoryMapper::toDTO));
    }

    @Override
    public CategoryResDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Category not found"));
        return categoryMapper.toDTO(category);
    }

    @Override
    public CategoryResDTO updateCategory(Long id, CategoryReqDTO categoryReqDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Category not found"));
        return saveCategory(category, categoryReqDTO);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ErrorException(HttpStatusEnum.BAD_REQUEST, "Category not found"));
        category.setIsDeleted(true);
        categoryRepository.save(category);
    }

    private CategoryResDTO saveCategory(Category category, CategoryReqDTO categoryReqDTO){
        category.setName(categoryReqDTO.getName());
        category.setDescription(categoryReqDTO.getDescription());
        return categoryMapper.toDTO(categoryRepository.save(category));
    }
}
