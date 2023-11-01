package net.javaguides.springboot.controller;

import jakarta.validation.Valid;
import net.javaguides.springboot.dto.CommentDto;
import net.javaguides.springboot.dto.PostDto;
import net.javaguides.springboot.entity.Post;
import net.javaguides.springboot.service.CommentService;
import net.javaguides.springboot.service.PostService;
import net.javaguides.springboot.util.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GuestController {

    private PostService postService;
    private CommentService commentService;

    public GuestController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    // create handler method, GET request and return model and view
    @GetMapping("/guest/posts")
    public String guestPosts(Model model){
        String role = SecurityUtils.getRole();
        List<PostDto> posts = postService.findPostsByUser();
        model.addAttribute("posts", posts);
        return "/guest/posts";
    }


    // create one method which guest role can see every post not like GUEST role

    // handler method to handle list comments request
    @GetMapping("/guest/posts/comments")
    public String postComments(Model model) {
        String role = SecurityUtils.getRole();
        List<CommentDto> comments = commentService.findCommentsByPost();
        model.addAttribute("comments", comments);
        return "guest/comments";
    }

    @GetMapping("/guest/posts/comments/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/guest/posts/comments";
    }

    // handler method to handle new post request
    @GetMapping("guest/posts/newpost")
    public String newPostForm(Model model){
        PostDto postDto = new PostDto();
        model.addAttribute("post", postDto);
        return "guest/create_post";
    }

    // handler method to handle form submit request
    @PostMapping("/guest/posts")
    public String createPost(@Valid @ModelAttribute("post") PostDto postDto,
                             BindingResult result,
                             Model model){
        if(result.hasErrors()){
            model.addAttribute("post", postDto);
            return "guest/create_post";
        }
        postDto.setUrl(getUrl(postDto.getTitle()));
        postService.createPost(postDto);
        return "redirect:/guest/posts";
    }

    // handler method to handle edit post request
    @GetMapping("/guest/posts/{postId}/edit")
    public String editPostForm(@PathVariable("postId") Long postId,
                               Model model){

        PostDto postDto = postService.findPostById(postId);
        model.addAttribute("post", postDto);
        return "guest/edit_post";
    }

    // handler method to handle delete post request


    // handler method to handle edit post form submit request
    @PostMapping("/guest/posts/{postId}")
    public String updatePost(@PathVariable("postId") Long postId,
                             @Valid @ModelAttribute("post") PostDto postDto,
                             BindingResult result,
                             Model model){
        if(result.hasErrors()) {
            model.addAttribute("post", postDto);
            return "guest/edit_post";
        }

        postDto.setId(postId);
        postService.updatePost(postDto);
        return "redirect:/guest/posts";
    }

    // handler method to handle delete
    @GetMapping("/guest/posts/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId);
        return "redirect:/guest/posts";
    }

    // handler method to handle view post request
    @GetMapping("/guest/{postId}")
    public String viewPost(@PathVariable("postId") Long postId, Model model) {
        PostDto postDto = postService.findPostById(postId);
        model.addAttribute("post", postDto);
        return "guest/view_post";
    }

    // handler method to handle view post request
    @GetMapping("/guest/posts/{postUrl}/view")
    public String viewPostByUrl(@PathVariable("postUrl") String postUrl,
                           Model model){
        PostDto postDto = postService.findPostByUrl(postUrl);
        model.addAttribute("post", postDto);
        return "guest/view_post";
    }


    // handler method to handle search blog posts request
    // localhost:8080/guest/posts/search?query=java
    @GetMapping("/guest/posts/search")
    public String searchPosts(@RequestParam(value = "query") String query,
                              Model model){
        List<PostDto> posts = postService.searchPosts(query);
        model.addAttribute("posts", posts);
        return "guest/posts";
    }

    private static String getUrl(String postTitle){
        // OOPS Concepts Explained in Java
        // oops-concepts-explained-in-java
        String title = postTitle.trim().toLowerCase();
        String url = title.replaceAll("\\s+", "-");
        url = url.replaceAll("[^A-Za-z0-9]", "-");
        return url;
    }
}
