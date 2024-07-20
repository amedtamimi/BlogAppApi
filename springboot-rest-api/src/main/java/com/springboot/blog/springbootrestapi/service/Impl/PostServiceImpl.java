package com.springboot.blog.springbootrestapi.service.Impl;

import com.springboot.blog.springbootrestapi.dto.CommentDto;
import com.springboot.blog.springbootrestapi.dto.PostDto;
import com.springboot.blog.springbootrestapi.entity.Category;
import com.springboot.blog.springbootrestapi.entity.Post;
import com.springboot.blog.springbootrestapi.entity.PostResponse;
import com.springboot.blog.springbootrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootrestapi.repository.CategoryRepository;
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
    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
        // convert DTO to entity
        Post post = mapToPostEntity(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);
        return mapToPostDto(newPost);
    }
    @Override
    public PostDto updatePost(PostDto postDto, Long id) {

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

       //Update the post entity in the database
       Post post = postRepository.findById(id).get();
         post.setTitle(postDto.getTitle());
         post.setContent(postDto.getContent());
         post.setDescription(postDto.getDescription());
         post.setCategory(category);
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

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream().map(this::mapToPostDto).collect(Collectors.toList());
    }


    private PostDto mapToPostDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }
    private Post mapToPostEntity(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

}
