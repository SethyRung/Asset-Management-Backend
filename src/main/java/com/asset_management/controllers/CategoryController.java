package com.asset_management.controllers;

import com.asset_management.dto.Category.CategoryReqDTO;
import com.asset_management.models.Category;
import com.asset_management.services.ICategoryService;
import com.asset_management.utils.PaginationPage;
import com.asset_management.utils.ResponseBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category")
@RestController
@RequestMapping(value = "/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseBody<Category>> addCategory(@RequestBody CategoryReqDTO categoryReqDTO) {
        return ResponseEntity.ok(new ResponseBody<>(categoryService.addCategory(categoryReqDTO)));
    }

    @GetMapping
    public ResponseEntity<ResponseBody<PaginationPage<Category>>> getAllCategory(@RequestParam(required = false) String search,
                                                                                 @RequestParam(defaultValue = "0") int page,
                                                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(new ResponseBody<>(categoryService.getAllCategory(search, page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBody<Category>> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseBody<>(categoryService.getCategoryById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody<Category>> updateCategory(@PathVariable Long id, @RequestBody CategoryReqDTO categoryReqDTO) {
        return ResponseEntity.ok(new ResponseBody<>(categoryService.updateCategory(id, categoryReqDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBody<?>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ResponseBody<>());
    }
}
