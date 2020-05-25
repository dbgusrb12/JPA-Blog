package com.jpa.jpaBlog.jpaBlog.comment.controller;

import com.jpa.jpaBlog.core.exception.NotFoundException;
import com.jpa.jpaBlog.jpaBlog.comment.entity.Comment;
import com.jpa.jpaBlog.jpaBlog.comment.entity.CommentDto;
import com.jpa.jpaBlog.jpaBlog.comment.service.CommentService;
import com.jpa.jpaBlog.jpaBlog.post.entity.Post;
import com.jpa.jpaBlog.jpaBlog.post.repository.PostRepository;
import com.jpa.jpaBlog.jpaBlog.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/comments")
public class CommentController {
    private final CommentService commentService;
    private final PostRepository postRepository;

    @ModelAttribute
    public Post post(@ModelAttribute CommentDto commentDto){
        return postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new NotFoundException(commentDto.getPostId() + "not found"));
    }

    @PostMapping
    public String createComment(@ModelAttribute @Valid CommentDto commentDto, BindingResult bindingResult, Model model, @AuthenticationPrincipal User user) {
        if(bindingResult.hasErrors()){
            return "post/post";
        }
        model.addAttribute("comment",
                commentService.createComment(
                        new Comment(commentDto.getContent(),
                                new Post(commentDto.getPostId()), user)
                )
        );
        return "redirect:/posts/" + commentDto.getPostId();
    }

    @PostMapping(value = "/{postId}/{commentId}")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return "redirect:/posts/" + postId;
    }

}
