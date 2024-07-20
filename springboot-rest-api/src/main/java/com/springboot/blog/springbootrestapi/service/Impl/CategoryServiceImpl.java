package com.springboot.blog.springbootrestapi.service.Impl;

import com.springboot.blog.springbootrestapi.dto.CategoryDto;
import com.springboot.blog.springbootrestapi.entity.Category;
import com.springboot.blog.springbootrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootrestapi.repository.CategoryRepository;
import com.springboot.blog.springbootrestapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository  categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = mapToCategoryEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return mapToCategoryDto(savedCategory);

    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return mapToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
       List<Category> categories = categoryRepository.findAll();
       return categories.stream().map(this::mapToCategoryDto).toList();
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
       Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
         Category updatedCategory = categoryRepository.save(category);
            return mapToCategoryDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(category);
    }

    private Category mapToCategoryEntity(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }
    private CategoryDto mapToCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }
}
