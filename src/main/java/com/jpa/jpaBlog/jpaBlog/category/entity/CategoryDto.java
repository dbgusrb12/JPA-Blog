package com.jpa.jpaBlog.jpaBlog.category.entity;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDto {

    private Long id;

    @NotBlank
    private String name;

    @Builder
    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
