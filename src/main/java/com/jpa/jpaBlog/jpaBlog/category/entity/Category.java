package com.jpa.jpaBlog.jpaBlog.category.entity;

import com.jpa.jpaBlog.jpaBlog.post.entity.Post;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString(exclude = {"post"})
@EqualsAndHashCode(exclude = {"post"})
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime regDate;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Post> post = new ArrayList<>();

    @Builder
    public Category(Long id, String name, LocalDateTime regDate, List<Post> post) {
        this.id = id;
        this.name = name;
        this.regDate = regDate;
        this.post = post;
    }

    public void updateCategory(CategoryDto categoryDto) {
        this.name = categoryDto.getName();
    }
}
