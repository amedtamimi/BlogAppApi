package com.springboot.blog.springbootrestapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private Long id;
    //title should not be empty
    //title should have at least 2 characters
    @NotEmpty(message = "Title is required")
    @Size(min = 2, message = "Title should have at least 2 characters")
    private String title;

    //content should not be empty
    //content should have at least 10 characters
    @NotEmpty(message = "Content is required")
    private String content;

    //description should not be empty
    //description should have at least 10 characters
    @NotEmpty(message = "Description is required")
    @Size(min = 10, message = "Description should have at least 10 characters")
    private String description;

    private Set<CommentDto> comments;

    private Long categoryId;


}
