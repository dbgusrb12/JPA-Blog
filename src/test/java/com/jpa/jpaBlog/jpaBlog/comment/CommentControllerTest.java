package com.jpa.jpaBlog.jpaBlog.comment;

import com.jpa.jpaBlog.core.security.SecurityConfig;
import com.jpa.jpaBlog.jpaBlog.comment.controller.CommentController;
import com.jpa.jpaBlog.jpaBlog.comment.entity.Comment;
import com.jpa.jpaBlog.jpaBlog.comment.service.CommentService;
import com.jpa.jpaBlog.jpaBlog.post.entity.Post;
import com.jpa.jpaBlog.jpaBlog.post.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CommentController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
@WithMockUser(username = "dbgusrb12")
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private PostRepository postRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Post post = Post.builder()
                .id(1L)
                .build();
        Optional<Post> newPost = Optional.of(post);
        given(postRepository.findByIdAndStatus(anyLong(), any())).willReturn(newPost);
    }

    @Test
    public void createComment() throws Exception {
//        CommentDto commentDto = CommentDto.builder()
//                .postId(1L)
//                .content("test")
//                .build();
        Comment comment = Comment.builder()
                .id(1L)
                .content("test")
                .post(Post.builder().id(1L).build())
                .regDate(LocalDateTime.now())
                .build();
        given(commentService.createComment(any(), any())).willReturn(comment);

        this.mvc.perform(post("/comments").with(csrf())
                .param("postId", "1")
                .param("content", "test"))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/posts/1"));
    }

    @Test
    public void deleteComment() throws Exception {
        this.mvc.perform(post("/comments/1/1").with(csrf()))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/posts/1"));
    }

}