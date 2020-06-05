package com.jpa.jpaBlog.jpaBlog.comment.service;

import com.jpa.jpaBlog.jpaBlog.comment.entity.Comment;
import com.jpa.jpaBlog.jpaBlog.comment.entity.CommentDto;
import com.jpa.jpaBlog.jpaBlog.comment.repository.CommentRepository;
import com.jpa.jpaBlog.jpaBlog.post.entity.Post;
import com.jpa.jpaBlog.jpaBlog.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment createComment(CommentDto commentDto, User user){
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .post(
                        Post.builder()
                        .id(commentDto.getPostId())
                        .build()
                )
                .regDate(LocalDateTime.now())
                .user(user)
                .build();
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId){
        commentRepository.deleteById(commentId);
    }
}
