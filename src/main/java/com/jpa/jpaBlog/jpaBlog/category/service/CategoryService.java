package com.jpa.jpaBlog.jpaBlog.category.service;

import com.jpa.jpaBlog.core.exception.NotFoundException;
import com.jpa.jpaBlog.jpaBlog.category.entity.Category;
import com.jpa.jpaBlog.jpaBlog.category.entity.CategoryDto;
import com.jpa.jpaBlog.jpaBlog.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto categoryDto){
        Category category = Category.builder()
                .name(categoryDto.getName())
                .regDate(LocalDateTime.now())
                .build();
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public void updateCategory(CategoryDto categoryDto){
        Category oldCategory = categoryRepository.findById(categoryDto.getId()).orElseThrow(() -> new NotFoundException(categoryDto.getId() + " not found"));
        oldCategory.updateCategory(categoryDto);
    }

    @Transactional(readOnly = true)
//    @Cacheable("blog.category")
    public Page<Category> findAll(Pageable pageable){
//        log.info("blog.category cache");
        return categoryRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findOne(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException(id + " not found"));
    }
}
