package com.springboot.blog.springbootrestapi.controller;

import com.springboot.blog.springbootrestapi.dto.CommentDto;
import com.springboot.blog.springbootrestapi.dto.PostDto;
import com.springboot.blog.springbootrestapi.entity.PostResponse;
import com.springboot.blog.springbootrestapi.service.Impl.PostServiceImpl;
import com.springboot.blog.springbootrestapi.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/posts")
public class PostController {
    private final PostServiceImpl postService;

    @Autowired
    public PostController(PostServiceImpl postService) {
        this.postService = postService;

    }
    //add all the crud methods for the post
    @PostMapping()
   public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return ResponseEntity.ok(postService.createPost(postDto));
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<PostDto>updatePost(@Valid @RequestBody PostDto postDto){
        return ResponseEntity.ok(postService.updatePost(postDto, postDto.getId()));
    }

    @GetMapping()
    public ResponseEntity<PostResponse>getAllPosts
            (@RequestParam(value ="PagNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false)int page,
             @RequestParam(value = "PageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int PageSize
             ,@RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy
            ,@RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir)
    {
        return ResponseEntity.ok(postService.getAllPosts(page,PageSize,sortBy,sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto>getPostById(@PathVariable Long id){
        return ResponseEntity.ok(postService.getPostById(id));

    }

    @DeleteMapping("/{id}")
    public void deletePostById(@PathVariable Long id){
        postService.deletePostById(id);
    }



}
