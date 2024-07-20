package com.springboot.blog.springbootrestapi.repository;

import com.springboot.blog.springbootrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    //add all the crud methods for the post entity
    //find by CategoryId
    List<Post> findByCategoryId(Long categoryId);
}
