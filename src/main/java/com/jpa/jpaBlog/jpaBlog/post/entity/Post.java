package com.jpa.jpaBlog.jpaBlog.post.entity;

import com.jpa.jpaBlog.jpaBlog.category.entity.Category;
import com.jpa.jpaBlog.jpaBlog.comment.entity.Comment;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    @Lob
    @NotNull
    private String content;

    @Lob
    private String code;

    @Enumerated
    private PostStatus status;

    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments;

    public Post(){
    }

    public Post(Long id){
        this.id = id;
    }

    public Post(String title, PostStatus status){
        this.title = title;
        this.status = status;
    }

    public Post(Long id, String title, String content, String code, PostStatus status){
        this.id = id;
        this.title = title;
        this.content = content;
        this.code = code;
        this.status = status;
    }

    public Post(String title, String content, String code, PostStatus status){
        this.title = title;
        this.content = content;
        this.code = code;
        this.status = status;
    }

    public Post(String title, String content, String code, PostStatus status, Category category){
        this.title = title;
        this.content = content;
        this.code = code;
        this.status = status;
        this.category = category;
    }

}
