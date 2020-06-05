package com.jpa.jpaBlog.jpaBlog.category.controller;

import com.jpa.jpaBlog.core.config.Navigation;
import com.jpa.jpaBlog.core.config.Section;
import com.jpa.jpaBlog.jpaBlog.category.entity.CategoryDto;
import com.jpa.jpaBlog.jpaBlog.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/categories")
@Navigation(Section.CATEGORY)
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String categories(Pageable pageable, Model model) {
        model.addAttribute("categories", categoryService.findAll(pageable));
        return "category/list";
    }

    @GetMapping(value = "/new")
    public String newCategory(@ModelAttribute CategoryDto categoryDto) {
        return "category/new";
    }

    @GetMapping(value = "/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("categoryDto", categoryService.findOne(id));
        return "category/edit";
    }

    @PostMapping
    public String createCategory(@ModelAttribute @Valid CategoryDto categoryDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "category/new";
        }
        categoryService.createCategory(categoryDto);
        return "redirect:/categories";
    }

    @PostMapping(value = "/{id}/edit")
    public String modifyCategory(@PathVariable Long id, @ModelAttribute @Valid CategoryDto categoryDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "category/edit";
        }
        categoryDto.setId(id);
        categoryService.updateCategory(categoryDto);
        return "redirect:/categories";
    }

    @PostMapping(value = "/{id}/delete")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }

}
