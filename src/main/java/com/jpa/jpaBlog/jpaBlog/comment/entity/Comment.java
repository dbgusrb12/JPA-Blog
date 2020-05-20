package com.jpa.jpaBlog.jpaBlog.comment.entity;

import com.jpa.jpaBlog.jpaBlog.post.entity.Post;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    public Comment(){

    }

    public Comment(String content, Post post){
        this.content = content;
        this.post = post;
    }

}
