package com.asset_management.controllers;

import com.asset_management.dto.Category.CategoryReqDTO;
import com.asset_management.dto.Category.CategoryResDTO;
import com.asset_management.models.Category;
import com.asset_management.services.ICategoryService;
import com.asset_management.utils.PaginationPage;
import com.asset_management.utils.ResponseBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Categories")
@RestController
@RequestMapping(value = "/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseBody<CategoryResDTO>> addCategory(@RequestBody CategoryReqDTO categoryReqDTO) {
        return ResponseEntity.ok(new ResponseBody<>(categoryService.addCategory(categoryReqDTO)));
    }

    @GetMapping
    public ResponseEntity<ResponseBody<PaginationPage<CategoryResDTO>>> getAllCategory(@RequestParam(required = false) String search,
                                                                                 @RequestParam(defaultValue = "0") int page,
                                                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(new ResponseBody<>(categoryService.getAllCategory(search, page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<CategoryResDTO>> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseBody<>(categoryService.getCategoryById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody<CategoryResDTO>> updateCategory(@PathVariable Long id, @RequestBody CategoryReqDTO categoryReqDTO) {
        return ResponseEntity.ok(new ResponseBody<>(categoryService.updateCategory(id, categoryReqDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody<?>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ResponseBody<>());
    }
}
