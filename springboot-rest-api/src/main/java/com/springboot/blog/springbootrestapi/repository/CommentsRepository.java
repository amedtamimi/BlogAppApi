package com.springboot.blog.springbootrestapi.repository;

import com.springboot.blog.springbootrestapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);

}
