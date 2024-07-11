package com.springboot.blog.springbootrestapi.service.Impl;

import com.springboot.blog.springbootrestapi.dto.CommentDto;
import com.springboot.blog.springbootrestapi.entity.Comment;
import com.springboot.blog.springbootrestapi.entity.Post;
import com.springboot.blog.springbootrestapi.exception.BlogApiException;
import com.springboot.blog.springbootrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootrestapi.repository.CommentsRepository;
import com.springboot.blog.springbootrestapi.repository.PostRepository;
import com.springboot.blog.springbootrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentsRepository commentRepository;
    private final PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentsRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = mapToCommentEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return mapToCommentDto(savedComment);

    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapToCommentDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        return commentRepository.findByPostId(postId).stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .map(this::mapToCommentDto)
                .orElseThrow(() -> new BlogApiException(HttpStatus.BAD_REQUEST, "Comment not found for postId: " + postId + " and commentId: " + commentId));
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        return commentRepository.findByPostId(postId).stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .map(comment -> {
                    comment.setBody(commentDto.getBody());
                    comment.setEmail(commentDto.getEmail());
                    comment.setName(commentDto.getName());
                    Comment updatedComment = commentRepository.save(comment);
                    return mapToCommentDto(updatedComment);
                })
                .orElseThrow(() -> new BlogApiException(HttpStatus.BAD_REQUEST, "Comment not found for postId: " + postId + " and commentId: " + commentId));

    }
    @Override
    public void deleteComment(Long postId, Long commentId) {
        commentRepository.findByPostId(postId).stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .ifPresentOrElse(commentRepository::delete,
                        () -> {
                            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment not found for postId: " + postId + " and commentId: " + commentId);
                        });
    }


    //map the comment entity to the comment dto
    private CommentDto mapToCommentDto(Comment comment){
        return modelMapper.map(comment, CommentDto.class);
    }
    private Comment mapToCommentEntity(CommentDto commentDto){
        return modelMapper.map(commentDto, Comment.class);
    }
}
