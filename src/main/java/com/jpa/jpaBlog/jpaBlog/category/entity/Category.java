package com.jpa.jpaBlog.jpaBlog.category.entity;

import com.jpa.jpaBlog.jpaBlog.post.entity.Post;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
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

    public Category(){

    }

    public Category(Long id){
        this.id = id;
    }

    public Category(Long id, String name){
        this.name = name;
        this.id = id;
    }

}
