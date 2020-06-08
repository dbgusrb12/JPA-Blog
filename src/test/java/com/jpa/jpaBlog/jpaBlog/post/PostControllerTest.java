package com.jpa.jpaBlog.jpaBlog.post;

import com.jpa.jpaBlog.jpaBlog.post.controller.PostController;
import com.jpa.jpaBlog.jpaBlog.post.entity.Post;
import com.jpa.jpaBlog.jpaBlog.post.entity.PostDto;
import com.jpa.jpaBlog.jpaBlog.post.entity.PostStatus;
import com.jpa.jpaBlog.jpaBlog.post.service.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    @Test
    public void findByPost() throws Exception {
        given(this.postService.findByIdAndStatus(
                anyLong(),
                anyObject())).willReturn(
                        Post.builder()
                                .title("제목")
                                .content("컨텐츠")
                                .code("마크다운")
                                .status(PostStatus.Y)
                                .build()
        );
        MvcResult mvcResult = this.mvc.perform(get("/posts/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        Post post = (Post) mvcResult.getModelAndView().getModel().get("post");
        assertThat(post.getTitle()).isEqualTo("제목");
        assertThat(post.getContent()).isEqualTo("컨텐츠");
        assertThat(post.getCode()).isEqualTo("마크다운");
        assertThat(post.getStatus()).isEqualTo(PostStatus.Y);
    }

    @Test
    public void newPost() throws Exception {
        this.mvc.perform(get("/posts/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("post/new"))
                .andReturn();
    }

    @Test
    public void editPost() throws Exception {
        given(this.postService.findByIdAndStatus(anyLong(), anyObject()))
                .willReturn(
                        Post.builder()
                                .title("제목")
                                .content("컨텐츠")
                                .code("마크다운")
                                .status(PostStatus.Y)
                                .build()
                );
        MvcResult mvcResult = this.mvc.perform(get("/posts/edit/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        PostDto postDto = (PostDto) mvcResult.getModelAndView().getModel().get("editPost");
        assertThat(postDto.getTitle()).isEqualTo("제목");
        assertThat(postDto.getContent()).isEqualTo("컨텐츠");
        assertThat(postDto.getCode()).isEqualTo("마크다운");
    }

    @Test
    public void editPostNotFoundException() throws Exception {
        given(this.postService.findByIdAndStatus(
                1L,
                PostStatus.Y)).willReturn(
                        Post.builder()
                            .title("제목")
                            .content("컨텐츠")
                            .code("마크다운")
                            .status(PostStatus.Y)
                            .build()
        );
        this.mvc.perform(get("/posts/edit/{id}", 2))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createPost() throws Exception {
        Post post = Post.builder()
                .title("제목")
                .content("컨텐츠")
                .code("마크다운")
                .status(PostStatus.Y)
                .build();
        given(postService.createPost(any(), any())).willReturn(post);

        this.mvc.perform(post("/posts")
                .param("title", "제목1")
                .param("categoryId", "1")
                .param("content", "컨텐츠1")
                .param("code", "마크다운1"))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/posts/1"));
    }

    @Test
    public void createPostValid() throws Exception {
        this.mvc.perform(post("/posts")
                .param("title", "제목1")
                .param("code", "마크다운1"))
                .andExpect(view().name("post/new"));
    }

    @Test
    public void modifyPost() throws Exception {
        Post post = Post.builder()
                .title("제목")
                .content("컨텐츠")
                .code("마크다운")
                .status(PostStatus.Y)
                .build();
        given(postService.updatePost(any(), any())).willReturn(post);

        this.mvc.perform(post("/posts/{id}/edit", 1L)
                .param("title", "제목2")
                .param("categoryId", "1")
                .param("content", "컨텐츠2")
                .param("code", "마크다운2"))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/posts/1"));
    }

    @Test
    public void deletePost() throws Exception {
        doNothing().when(postService).deletePost(anyLong());
        this.mvc.perform(post("/posts/{id}/delete", 1L))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/#/"));
    }

}
