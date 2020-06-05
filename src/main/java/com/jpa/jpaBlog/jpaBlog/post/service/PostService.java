package com.jpa.jpaBlog.jpaBlog.post.service;

import com.jpa.jpaBlog.core.exception.NotFoundException;
import com.jpa.jpaBlog.jpaBlog.category.entity.Category;
import com.jpa.jpaBlog.jpaBlog.post.entity.Post;
import com.jpa.jpaBlog.jpaBlog.post.entity.PostDto;
import com.jpa.jpaBlog.jpaBlog.post.entity.PostStatus;
import com.jpa.jpaBlog.jpaBlog.post.repository.PostRepository;
import com.jpa.jpaBlog.jpaBlog.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post createPost(PostDto createPost, User user){
        Post post = Post.builder()
                .title(createPost.getTitle())
                .content(createPost.getContent())
                .status(PostStatus.Y)
                .regDate(LocalDateTime.now())
                .category(
                        Category.builder()
                                .id(createPost.getCategoryId())
                                .build()
                )
                .user(user)
                .build();
        return postRepository.save(post);
    }

    public Post updatePost(PostDto post, User user){
        Post oldPost = postRepository.findByIdAndStatus(post.getId(), PostStatus.Y).orElseThrow(() -> new NotFoundException(post.getId() + " not found"));
        oldPost.updatePost(post, user);
        return oldPost;
    }

    public void deletePost(Long id){
        Post oldPost = postRepository.findByIdAndStatus(id, PostStatus.Y).orElseThrow(() -> new NotFoundException(id + " not found"));
        oldPost.deletePost();
    }

    @Transactional(readOnly = true)
    public Post findByIdAndStatus(Long id, PostStatus status){
        return postRepository.findByIdAndStatus(id, status).orElseThrow(() -> new NotFoundException(id + " not found"));
    }

}
