package net.javaguides.springboot.controller;

import net.javaguides.springboot.dto.CommentDto;
import net.javaguides.springboot.dto.PostDto;
import net.javaguides.springboot.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// For client side!
@Controller
public class BlogController {

    private PostService postService;

    public BlogController(PostService postService) {
        this.postService = postService;
    }

    // handler method to handle http://localhost:8080/
    @GetMapping("/")
    public String viewBlogPosts(Model model) {
        List<PostDto> postsResponse = postService.findAllPosts();
        model.addAttribute("postsResponse", postsResponse);
        return "blog/home";
    }

    // handler method to handle view post request
    @GetMapping("/{postId}")
    public String viewPost(@PathVariable("postId") Long postId, Model model) {
        PostDto postDto = postService.findPostById(postId);
        CommentDto commentDto = new CommentDto();
        model.addAttribute("post", postDto);
        model.addAttribute("comment", commentDto);
        return "blog/blog_post";
    }

    // handler method to handle view post request
    @GetMapping("/post/{postUrl}")
    private String showPostByUrl(@PathVariable("postUrl") String postUrl,
                            Model model) {
        PostDto post = postService.findPostByUrl(postUrl);

        CommentDto commentDto = new CommentDto();
        model.addAttribute("post", post);
        model.addAttribute("comment", commentDto);
        return "blog/blog_post";
    }

    // handler method to handle blog post search request
    // http://localhost:8080/page/search?query=java
    @GetMapping("/page/search")
    public String searchPosts(@RequestParam(value = "query") String query,
                              Model model){
        List<PostDto> postsResponse = postService.searchPosts(query);
        model.addAttribute("postsResponse", postsResponse);
        return "blog/home";
    }
}
