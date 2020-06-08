package com.jpa.jpaBlog.jpaBlog.comment.entity;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {

    @NotNull
    private Long postId;

    @NotBlank
    private String content;

    @Builder
    public CommentDto(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }

}
