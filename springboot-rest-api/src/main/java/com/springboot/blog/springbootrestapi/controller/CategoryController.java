package com.springboot.blog.springbootrestapi.controller;

import com.springboot.blog.springbootrestapi.dto.CategoryDto;
import com.springboot.blog.springbootrestapi.service.CategoryService;
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
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
       return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    //TODO: Implement the getCategory method
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable(value = "id") Long id){
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    //TODO: Implement the getAllCategories method
    @GetMapping()
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    //TODO: Implement the updateCategory method
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryDto, id));
    }

    //TODO: Implement the deleteCategory method
    @DeleteMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(@PathVariable(value = "id") Long id){
        categoryService.deleteCategory(id);
    }


}
