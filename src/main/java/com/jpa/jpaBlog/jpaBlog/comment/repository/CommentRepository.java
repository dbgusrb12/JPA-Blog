package com.jpa.jpaBlog.jpaBlog.comment.repository;

import com.jpa.jpaBlog.jpaBlog.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
