package com.jpa.jpaBlog.jpaBlog.category.service;

import com.jpa.jpaBlog.core.exception.NotFoundException;
import com.jpa.jpaBlog.jpaBlog.category.entity.Category;
import com.jpa.jpaBlog.jpaBlog.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(Category category){
        category.setRegDate(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public void updateCategory(Category category){
        Category oldCategory = categoryRepository.findById(category.getId()).orElseThrow(() -> new NotFoundException(category.getId() + " not found"));

        oldCategory.setName(category.getName());
    }

    @Transactional(readOnly = true)
    public Page<Category> findAll(Pageable pageable){
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
