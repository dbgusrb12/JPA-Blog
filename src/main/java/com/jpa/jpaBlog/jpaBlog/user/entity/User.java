package com.jpa.jpaBlog.jpaBlog.user.entity;

import com.jpa.jpaBlog.jpaBlog.comment.entity.Comment;
import com.jpa.jpaBlog.jpaBlog.post.entity.Post;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString(exclude = {"comments", "post"})
@EqualsAndHashCode(exclude = {"comments", "post"})
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String name;

    private String github;

    private String avatarUrl;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> post = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private  List<Comment> comments = new ArrayList<>();

    @Column
    @Lob
    private String bio;

    public User(String email, String name, String github, String avatarUrl) {
        this.email = email;
        this.name = name;
        this.github = github;
        this.avatarUrl = avatarUrl;
    }

    public User() {

    }

}
