package com.jpa.jpaBlog.jpaBlog.post.controller;

import com.jpa.jpaBlog.core.config.Navigation;
import com.jpa.jpaBlog.core.config.Section;
import com.jpa.jpaBlog.core.exception.NotFoundException;
import com.jpa.jpaBlog.jpaBlog.category.entity.Category;
import com.jpa.jpaBlog.jpaBlog.category.service.CategoryService;
import com.jpa.jpaBlog.jpaBlog.comment.entity.CommentDto;
import com.jpa.jpaBlog.jpaBlog.post.entity.Post;
import com.jpa.jpaBlog.jpaBlog.post.entity.PostDto;
import com.jpa.jpaBlog.jpaBlog.post.entity.PostStatus;
import com.jpa.jpaBlog.jpaBlog.post.service.PostService;
import com.jpa.jpaBlog.jpaBlog.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/posts")
@Navigation(Section.POST)
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;

    @ModelAttribute("categories")
    public List<Category> categories(){
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public String findByPost(@PathVariable Long id, Model model, @ModelAttribute CommentDto commentDto) {
        Post post = postService.findByIdAndStatus(id, PostStatus.Y);
        if(ObjectUtils.isEmpty(post)) {
            throw new NotFoundException(id + " not found");
        }
        model.addAttribute("post", post);
        return "post/post";
    }

    @GetMapping("/new")
    public String newPost(PostDto postDto) {
        return "post/new";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable Long id, Model model) {
        Post post = postService.findByIdAndStatus(id, PostStatus.Y);
        if(ObjectUtils.isEmpty(post)) {
            throw new NotFoundException(id + " not found");
        }
        PostDto createPost = PostDto.builder()
                .categoryId(post.getCategory().getId())
                .categoryName(post.getCategory().getName())
                .title(post.getTitle())
                .code(post.getCode())
                .content(post.getContent())
                .id(post.getId())
                .build();

        model.addAttribute("editPost", createPost);
        return "post/edit";
    }

    @PostMapping
    public String createPost(@ModelAttribute @Valid PostDto createPost, BindingResult bindingResult, Model model, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return "post/new";
        }

        Post newPost = postService.createPost(createPost, user);
        model.addAttribute("post", newPost);
        return "redirect:/posts/" + newPost.getId();
    }

    @PostMapping("/{id}/edit")
    public String modifyPost(@PathVariable Long id, @ModelAttribute("editPost") @Valid PostDto createPost, BindingResult bindingResult, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return "post/edit";
        }
        createPost.setId(id);
        postService.updatePost(createPost, user);
        return "redirect:/posts/" + id;
    }

    @PostMapping("{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return "redirect:/#/";
    }

}
