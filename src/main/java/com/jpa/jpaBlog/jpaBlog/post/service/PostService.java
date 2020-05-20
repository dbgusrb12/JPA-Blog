package com.jpa.jpaBlog.jpaBlog.post.service;

import com.jpa.jpaBlog.core.exception.NotFoundException;
import com.jpa.jpaBlog.jpaBlog.post.entity.Post;
import com.jpa.jpaBlog.jpaBlog.post.entity.PostStatus;
import com.jpa.jpaBlog.jpaBlog.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post createPost(Post post){
        post.setRegDate(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post post){
        Post oldPost = postRepository.findByIdAndStatus(id, PostStatus.Y);
        if(ObjectUtils.isEmpty(oldPost)){
            throw new NotFoundException(id + "not found");
        }

        oldPost.setContent(post.getContent());
        oldPost.setCode(post.getCode());
        oldPost.setTitle(post.getTitle());
        return oldPost;
    }

    public void deletePost(Long id){
        Post oldPost = postRepository.findByIdAndStatus(id, PostStatus.Y);
        if(ObjectUtils.isEmpty(oldPost)){
            throw new NotFoundException(id + " not found");
        }
        oldPost.setStatus(PostStatus.N);
    }

    @Transactional(readOnly = true)
    public Post findByIdAndStatus(Long id, PostStatus status){
        Post post = postRepository.findByIdAndStatus(id, status);
        if (ObjectUtils.isEmpty(post)) {
            throw new NotFoundException(id + " not found");
        }
        return post;
    }

}
