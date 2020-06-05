package com.jpa.jpaBlog.jpaBlog.post.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String code;

    @NotNull
    private Long categoryId;

    private String categoryName;

    @Builder
    public PostDto(Long id, @NotBlank String title, @NotBlank String content, String code, @NotNull Long categoryId, String categoryName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.code = code;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
