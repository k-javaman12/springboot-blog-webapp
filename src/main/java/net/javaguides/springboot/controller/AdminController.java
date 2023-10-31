package net.javaguides.springboot.controller;

import jakarta.validation.Valid;
import net.javaguides.springboot.dto.CommentDto;
import net.javaguides.springboot.dto.PostDto;
import net.javaguides.springboot.service.CommentService;
import net.javaguides.springboot.service.PostService;
import net.javaguides.springboot.util.ROLE;
import net.javaguides.springboot.util.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    private PostService postService;
    private CommentService commentService;

    public AdminController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    // create handler method, GET request and return model and view
    @GetMapping("/admin/posts")
    public String adminPosts(Model model){
        String role = SecurityUtils.getRole();
        List<PostDto> posts = null;
        posts = postService.findAllPosts();
        model.addAttribute("posts", posts);
        return "/admin/posts";
    }

    // create handler method, GET request and return model and view
    @GetMapping("/admin/posts/comments")
    public String adminComments(Model model){
        String role = SecurityUtils.getRole();
        List<CommentDto> comments = null;
        comments = commentService.findAllComments();
        model.addAttribute("comments", comments);
        return "/admin/comments";
    }

    // handler method to handle edit post request
    @GetMapping("/admin/posts/{postId}/edit")
    public String AdminEditPostForm(@PathVariable("postId") Long postId,
                               Model model){

        PostDto postDto = postService.findPostById(postId);
        model.addAttribute("post", postDto);
        return "admin/edit_post";
    }

    // handler method to handle edit post form submit request
    @PostMapping("/admin/posts/{postId}")
    public String AdminUpdatePost(@PathVariable("postId") Long postId,
                             @Valid @ModelAttribute("post") PostDto postDto,
                             BindingResult result,
                             Model model){
        if(result.hasErrors()) {
            model.addAttribute("post", postDto);
            return "admin/edit_post";
        }

        postDto.setId(postId);
        postService.updatePost(postDto);
        return "redirect:/admin/posts";
    }

    // handler method to handle delete
    @GetMapping("/admin/posts/{postId}/delete")
    public String adminDeletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId);
        return "redirect:/admin/posts";
    }

    // handler method to handle view post request
    @GetMapping("/admin/{postId}")
    public String adminViewPost(@PathVariable("postId") Long postId, Model model) {
        PostDto postDto = postService.findPostById(postId);
        model.addAttribute("post", postDto);
        return "admin/view_post";
    }

    // handler method to handle view post request
    @GetMapping("/admin/posts/{postUrl}/view")
    public String adminViewPostByUrl(@PathVariable("postUrl") String postUrl,
                                Model model){
        PostDto postDto = postService.findPostByUrl(postUrl);
        model.addAttribute("post", postDto);
        return "admin/view_post";
    }

    // handler method to handle new post request
    @GetMapping("admin/posts/newpost")
    public String adminNewPostForm(Model model){
        PostDto postDto = new PostDto();
        model.addAttribute("post", postDto);
        return "admin/create_post";
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
