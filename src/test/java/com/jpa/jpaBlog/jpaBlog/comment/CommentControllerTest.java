package com.jpa.jpaBlog.jpaBlog.comment;

import com.jpa.jpaBlog.jpaBlog.comment.entity.Comment;
import com.jpa.jpaBlog.jpaBlog.comment.entity.CommentDto;
import com.jpa.jpaBlog.jpaBlog.comment.service.CommentService;
import com.jpa.jpaBlog.jpaBlog.post.entity.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @Test
    public void createComment() throws Exception {
        CommentDto commentDto = CommentDto.builder()
                .postId(1L)
                .content("test")
                .build();
        Comment comment = Comment.builder()
                .id(1L)
                .content("test")
                .post(Post.builder().id(1L).build())
                .build();
        given(commentService.createComment(commentDto, any())).willReturn(comment);

        this.mvc.perform(post("/comments")
                .param("postId", "1")
                .param("content", "test"))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/posts/1"));
    }

    @Test
    public void deleteComment() throws Exception {
        this.mvc.perform(post("/comments/1/1"))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, "/posts/1"));
    }

}