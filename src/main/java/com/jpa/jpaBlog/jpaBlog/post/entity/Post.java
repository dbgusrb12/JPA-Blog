package com.jpa.jpaBlog.jpaBlog.post.entity;

import com.jpa.jpaBlog.jpaBlog.category.entity.Category;
import com.jpa.jpaBlog.jpaBlog.comment.entity.Comment;
import com.jpa.jpaBlog.jpaBlog.user.entity.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString(exclude = {"category", "comments"})
@EqualsAndHashCode(exclude = {"category", "comments"})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @Lob
    @NotNull
    private String content;

    @Lob
    private String code;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;


    @Builder
    public Post(Long id, String title, String content, String code, PostStatus status, LocalDateTime regDate, Category category, List<Comment> comments, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.code = code;
        this.status = status;
        this.regDate = regDate;
        this.category = category;
        this.comments = comments;
        this.user = user;
    }

    public void updatePost(PostDto post, User user) {
        this.content = post.getContent();
        this.code = post.getCode();
        this.title = post.getTitle();
        this.category = Category.builder()
                .id(post.getCategoryId())
                .build();
        this.user = user;
    }

    public void deletePost() {
        this.status = PostStatus.N;
    }
}
