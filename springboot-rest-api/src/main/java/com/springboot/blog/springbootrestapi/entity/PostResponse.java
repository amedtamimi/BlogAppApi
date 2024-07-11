package com.springboot.blog.springbootrestapi.entity;

import com.springboot.blog.springbootrestapi.dto.PostDto;
import lombok.Data;

import java.util.List;
@Data
public class PostResponse {
    private List<PostDto> content;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private boolean lastPage;
}
