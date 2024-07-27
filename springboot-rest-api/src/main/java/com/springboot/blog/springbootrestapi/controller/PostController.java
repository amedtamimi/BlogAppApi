package com.springboot.blog.springbootrestapi.controller;

import com.springboot.blog.springbootrestapi.dto.PostDto;
import com.springboot.blog.springbootrestapi.entity.PostResponse;
import com.springboot.blog.springbootrestapi.service.Impl.PostServiceImpl;
import com.springboot.blog.springbootrestapi.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostServiceImpl postService;

    @Autowired
    public PostController(PostServiceImpl postService) {
        this.postService = postService;

    }


    //add all the crud methods for the post
    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
   public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Update Post REST API",
            description = "Update Post REST API is used to update post into database"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping({"/{id}"})
    public ResponseEntity<PostDto>updatePost(@Valid @RequestBody PostDto postDto){
        return ResponseEntity.ok(postService.updatePost(postDto, postDto.getId()));
    }

    @Operation(
            summary = "Get All Posts REST API",
            description = "Get All Posts REST API is used to get all posts from database"
    )
    @GetMapping()
    public ResponseEntity<PostResponse>getAllPosts
            (@RequestParam(value ="PagNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false)int page,
             @RequestParam(value = "PageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int PageSize
             ,@RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy
            ,@RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir)
    {
        return ResponseEntity.ok(postService.getAllPosts(page,PageSize,sortBy,sortDir));
    }


    @Operation(
            summary = "Get Post By Id REST API",
            description = "Get Post By Id REST API is used to get post by id from database"
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDto>getPostById(@PathVariable Long id){
        return ResponseEntity.ok(postService.getPostById(id));

    }


    @Operation(
            summary = "Delete Post By Id REST API",
            description = "Delete Post By Id REST API is used to delete post by id from database"
    )
    @DeleteMapping("/{id}")
    public void deletePostById(@PathVariable Long id){
        postService.deletePostById(id);
    }


    @Operation(
            summary = "Get Posts By Category REST API",
            description = "Get Posts By Category REST API is used to get posts by category from database"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable(value = "id") Long categoryId){
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
    }



}
