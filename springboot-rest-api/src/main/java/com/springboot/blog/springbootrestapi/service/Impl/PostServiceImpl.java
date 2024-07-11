package com.springboot.blog.springbootrestapi.service.Impl;

import com.springboot.blog.springbootrestapi.dto.CommentDto;
import com.springboot.blog.springbootrestapi.dto.PostDto;
import com.springboot.blog.springbootrestapi.entity.Post;
import com.springboot.blog.springbootrestapi.entity.PostResponse;
import com.springboot.blog.springbootrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootrestapi.repository.PostRepository;
import com.springboot.blog.springbootrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public PostDto createPost(PostDto postDto) {

        // convert DTO to entity
        Post post = mapToPostEntity(postDto);
        Post newPost = postRepository.save(post);
        return mapToPostDto(newPost);
    }
    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
       //Update the post entity in the database
       Post post = postRepository.findById(id).get();
         post.setTitle(postDto.getTitle());
         post.setContent(postDto.getContent());
         post.setDescription(postDto.getDescription());
         Post updatedPost = postRepository.save(post);

            //convert the updated post entity back to postDto and return it
            PostDto postResponse = new PostDto();
            postResponse.setId(updatedPost.getId());
            postResponse.setTitle(updatedPost.getTitle());
            postResponse.setContent(updatedPost.getContent());
            postResponse.setDescription(updatedPost.getDescription());
            return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToPostDto(post);
    }

    @Override
    public void deletePostById(Long id) {
        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);

    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        // get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content= listOfPosts.stream().map(this::mapToPostDto).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setCurrentPage(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLastPage(posts.isLast());

        return postResponse;
    }

    private PostDto mapToPostDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }
    private Post mapToPostEntity(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

}
