package com.jpa.jpaBlog.jpaBlog.post.repository;

import com.jpa.jpaBlog.jpaBlog.post.entity.Post;
import com.jpa.jpaBlog.jpaBlog.post.entity.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByIdAndStatus(Long id, PostStatus status);
}
