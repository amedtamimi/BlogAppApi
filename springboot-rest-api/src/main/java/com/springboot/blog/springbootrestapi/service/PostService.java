package com.springboot.blog.springbootrestapi.service;

import com.springboot.blog.springbootrestapi.dto.CommentDto;
import com.springboot.blog.springbootrestapi.dto.PostDto;
import com.springboot.blog.springbootrestapi.entity.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostDto updatePost(PostDto postDto, Long id);
    PostDto getPostById(Long id);
    void deletePostById(Long id);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy,String sortDir);

    List<PostDto> getPostsByCategory(Long categoryId);
}
