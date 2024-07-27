package com.springboot.blog.springbootrestapi.controller;

import com.springboot.blog.springbootrestapi.dto.CategoryDto;
import com.springboot.blog.springbootrestapi.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //TODO: Implement the category controller

    //TODO: Implement the createCategory method
    @Operation(
            summary = "Create Category REST API",
            description = "Create Category REST API is used to save category into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
       return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    //TODO: Implement the getCategory method
    @Operation(
            summary = "Get Category REST API",
            description = "Get Category REST API is used to get category from database"
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    //TODO: Implement the getAllCategories method
    @Operation(
            summary = "Get All Categories REST API",
            description = "Get All Categories REST API is used to get all categories from database"
    )
    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    //TODO: Implement the updateCategory method
    @Operation(
            summary = "Update Category REST API",
            description = "Update Category REST API is used to update category into database"
    )
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, id));
    }

    //TODO: Implement the deleteCategory method
    @Operation(
            summary = "Delete Category REST API",
            description = "Delete Category REST API is used to delete category from database"
    )
    @DeleteMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(@PathVariable(value = "id") Long id){
        categoryService.deleteCategory(id);
    }


}
