package com.jpa.jpaBlog.jpaBlog.category;

import com.jpa.jpaBlog.core.security.SecurityConfig;
import com.jpa.jpaBlog.jpaBlog.category.controller.CategoryController;
import com.jpa.jpaBlog.jpaBlog.category.entity.Category;
import com.jpa.jpaBlog.jpaBlog.category.entity.CategoryDto;
import com.jpa.jpaBlog.jpaBlog.category.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(controllers = CategoryController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
@WithMockUser(username = "dbgusrb12")
public class CategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void categories() throws Exception {
        List<Category> categories = Arrays.asList(
                Category.builder()
                        .id(1L)
                        .name("spring")
                        .build(),
                Category.builder()
                        .id(2L)
                        .name("jpa")
                        .build(),
                Category.builder()
                        .id(3L)
                        .name("java")
                        .build(),
                Category.builder()
                        .id(4L)
                        .name("spring-boot")
                        .build(),
                Category.builder()
                        .id(5L)
                        .name("javascript")
                        .build()
        );


        Page<Category> categoryPage = new PageImpl<>(categories);
        given(this.categoryService.findAll(any())).willReturn(categoryPage);
        MvcResult mvcResult = this.mvc.perform(get("/categories").with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        Page<Category> categoriesPage = (Page<Category>) mvcResult.getModelAndView().getModel().get("categories");
        List<Category> content = categoriesPage.getContent();
        assertThat(content.get(0).getId()).isEqualTo(1L);
        assertThat(content.get(0).getName()).isEqualTo("spring");
        assertThat(content.get(1).getId()).isEqualTo(2L);
        assertThat(content.get(1).getName()).isEqualTo("jpa");
        assertThat(content.get(2).getId()).isEqualTo(3L);
        assertThat(content.get(2).getName()).isEqualTo("java");
        assertThat(content.get(3).getId()).isEqualTo(4L);
        assertThat(content.get(3).getName()).isEqualTo("spring-boot");
        assertThat(content.get(4).getId()).isEqualTo(5L);
        assertThat(content.get(4).getName()).isEqualTo("javascript");
    }

    @Test
    public void newCategory() throws Exception {
        this.mvc.perform(get("/categories/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("category/new"))
                .andReturn();
    }

    @Test
    public void edit() throws Exception {
        given(categoryService.findOne(anyLong())).willReturn(
                Category.builder()
                        .id(1L)
                        .name("spring")
                        .build()
        );

        MvcResult mvcResult = this.mvc.perform(get("/categories/{id}/edit", 1).with(csrf()))
                .andExpect(status().isOk())
                .andReturn();

        Category category = (Category) mvcResult.getModelAndView().getModel().get("categoryDto");
        assertThat(category.getId()).isEqualTo(1L);
        assertThat(category.getName()).isEqualTo("spring");
    }

    @Test
    public void createCategory() throws Exception {
        CategoryDto categoryDto = CategoryDto.builder()
                .id(1L)
                .name("spring")
                .build();

        given(categoryService.createCategory(categoryDto))
                .willReturn(Category.builder()
                        .id(1L)
                        .name("spring")
                        .regDate(LocalDateTime.now())
                        .build()
                );

        this.mvc.perform(post("/categories").with(csrf())
                .param("name", "spring"))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/categories?navSection=Category"));
    }

    @Test
    public void modifyCategory() throws Exception {
        doNothing().when(categoryService).updateCategory(any());

        this.mvc.perform(post("/categories/{id}/edit", 1L).with(csrf())
                .param("name", "spring-boot"))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/categories?navSection=Category"));
    }

    @Test
    public void deleteCategory() throws Exception {
        doNothing().when(categoryService).delete(any());
        this.mvc.perform(post("/categories/{id}/delete", 1L).with(csrf()))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/categories?navSection=Category"));
    }
}