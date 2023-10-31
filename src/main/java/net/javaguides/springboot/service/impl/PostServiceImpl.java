package net.javaguides.springboot.service.impl;

import net.javaguides.springboot.dto.PostDto;
import net.javaguides.springboot.entity.Category;
import net.javaguides.springboot.entity.Post;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.mapper.PostMapper;
import net.javaguides.springboot.repository.CategoryRepository;
import net.javaguides.springboot.repository.PostRepository;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.PostService;
import net.javaguides.springboot.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<PostDto> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(PostMapper::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> findPostsByUser() {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User createdBy = userRepository.findByEmail(email);
        Long userId = createdBy.getId();
        List<Post> posts = postRepository.findPostsByUser(userId);
        return posts.stream()
                .map((post) -> PostMapper.mapToPostDto(post))
                .collect(Collectors.toList());
    }

    @Override
    public void createPost(PostDto postDto) {
        String email = Objects.requireNonNull(SecurityUtils.getCurrentUser()).getUsername();
        User user = userRepository.findByEmail(email);
        Post post = PostMapper.mapToPost(postDto);
        post.setCreatedBy(user);
        postRepository.save(post);
    }

    public PostDto findPostById(Long postId) {
        return postRepository.findById(postId)
                .map(PostMapper::mapToPostDto)
                .orElse(null); // or throw an exception
    }

    @Override
    public void updatePost(PostDto postDto) {
        String email = SecurityUtils.getCurrentUser().getUsername();
        User createdBy = userRepository.findByEmail(email);
        Post post = PostMapper.mapToPost(postDto);
        post.setCreatedBy(createdBy);
        postRepository.save(post);

    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public PostDto findPostByUrl(String postUrl) {

            // in order to get the post object, we use get()
            Post post = postRepository.findByUrl(postUrl).get();
            return PostMapper.mapToPostDto(post);

    }

    @Override
    public List<PostDto> searchPosts(String query) {
        List<Post> posts = postRepository.searchPosts(query);
        return posts.stream()
                .map(PostMapper::mapToPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> findPostsByCategoryName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName).orElse(null);
        if (category != null) {
            return category.getPosts();
        }
        return new ArrayList<>();
    }
}
