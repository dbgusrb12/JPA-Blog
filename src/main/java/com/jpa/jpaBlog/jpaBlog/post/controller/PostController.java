package com.jpa.jpaBlog.jpaBlog.post.controller;

import com.jpa.jpaBlog.core.exception.NotFoundException;
import com.jpa.jpaBlog.jpaBlog.category.entity.Category;
import com.jpa.jpaBlog.jpaBlog.post.entity.Post;
import com.jpa.jpaBlog.jpaBlog.post.entity.PostDto;
import com.jpa.jpaBlog.jpaBlog.post.entity.PostStatus;
import com.jpa.jpaBlog.jpaBlog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/posts")
public class PostController {
    private final PostService postService;

    @GetMapping(value = "/{id}")
    public String findByPost(@PathVariable Long id, Model model){
        Post post = postService.findByIdAndStatus(id, PostStatus.Y);
        if(ObjectUtils.isEmpty(post)){
            throw new NotFoundException(id + " not found");
        }
        model.addAttribute("post", post);
        return "post/post";
    }

    @GetMapping(value = "/new")
    public String newPost(PostDto postDto){
        return "post/new";
    }

    @GetMapping(value = "/edit/{id}")
    public String editPost(@PathVariable Long id, Model model){
        Post post = postService.findByIdAndStatus(id, PostStatus.Y);
        if(ObjectUtils.isEmpty(post)){
            throw new NotFoundException(id + " not found");
        }
        PostDto createPost = new PostDto();
        createPost.setTitle(post.getTitle());
        createPost.setCode(post.getCode());
        createPost.setContent(post.getContent());
        createPost.setId(id);
        model.addAttribute("editPost", createPost);
        return "post/edit";
    }

    @PostMapping
    public String createPost(@ModelAttribute @Valid PostDto createPost, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "post/new";
        }
        Post post = new Post(createPost.getTitle(),
                createPost.getContent(),
                createPost.getCode(),
                PostStatus.Y,
                new Category(createPost.getCategoryId()));
        Post newPost = postService.createPost(post);
        model.addAttribute("post", newPost);
        return "redirect:/posts/" + newPost.getId();
    }

    @PostMapping(value = "/{id}/edit")
    public String modifyPost(@PathVariable Long id, @ModelAttribute("editPost") @Valid PostDto createPost, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "post/edit";
        }
        postService.updatePost(id, new Post(
                createPost.getTitle(),
                createPost.getContent(),
                createPost.getCode(),
                PostStatus.Y,
                new Category(createPost.getCategoryId())
        ));
        return "redirect:/posts/" + id;
    }

    @PostMapping(value = "/{id}/delete")
    public String deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return "redirect:/#/";
    }



}
