package net.javaguides.springboot.controller;

import jakarta.validation.Valid;
import net.javaguides.springboot.dto.CommentDto;
import net.javaguides.springboot.dto.PostDto;
import net.javaguides.springboot.service.CommentService;
import net.javaguides.springboot.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    private CommentService commentService;

    private PostService postService;

    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }


    // handler method to create form submit request
    @PostMapping("/{postId}")
    public String createComment(@PathVariable("postId") Long postId,
                                @Valid @ModelAttribute("comment") CommentDto commentDto,
                                BindingResult result,
                                Model model) {

        PostDto postDto = postService.findPostById(postId);
        if (result.hasErrors()) {
            model.addAttribute("post",postDto);
            model.addAttribute("comment", commentDto);
            return "blog/blog_post";
        }
        commentService.createComment(postId, commentDto);
        return "redirect:/" + postId;
    }

    // handler method to create form submit request
//    @PostMapping("/{postUrl}/comments")
//    public String createComment(@PathVariable("postUrl") String postUrl,
//                                @ModelAttribute("comment") CommentDto commentDto,
//                                Model model) {
//        commentService.createComment(postUrl, commentDto);
//        return "redirect:/post/" + postUrl;
//    }
}
