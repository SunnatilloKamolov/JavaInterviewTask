package uz.job.javainterview.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import uz.job.javainterview.JavaInterviewApplication;
import uz.job.javainterview.dto.CommentDTO;
import uz.job.javainterview.service.BlogService;
import uz.job.javainterview.service.CommentService;
import uz.job.javainterview.util.TestUtil;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = JavaInterviewApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class CommentControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    CommentController commentController;
    @Autowired
    CommentService commentService;

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(commentController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    public void should_ReturnAllComments() throws Exception {
        commentService.addComment(new CommentDTO(1L, "Good job", LocalDateTime.now(), 10, 12, true, null));
        mvc.perform(get("/api/v1/comments")
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    public void should_ReturnCommentById() throws Exception {
        commentService.addComment(new CommentDTO(1L, "Good job", LocalDateTime.now(), 10, 12, true, null));
        mvc.perform(get("/api/v1/comment/{id}", 1L)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(Matchers.equalTo(1))))
                .andExpect(jsonPath("$.comment", Matchers.is(Matchers.equalTo("Good job"))))
                .andExpect(jsonPath("$.numberOfUtility", Matchers.is(Matchers.equalTo(10))))
                .andExpect(jsonPath("$.numberOfFutility", Matchers.is(Matchers.equalTo(12))))
                .andExpect(jsonPath("$.checkedComment", Matchers.is(Matchers.equalTo(true))))
                .andReturn();
    }

    @Test
    public void createComment() throws Exception {
        CommentDTO commentDTO = new CommentDTO(1L, "Good job", null, 10, 12, true, null);
        mvc.perform(post("/api/v1/comment")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(commentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(Matchers.equalTo(1))))
                .andExpect(jsonPath("$.comment", Matchers.is(Matchers.equalTo("Good job"))))
                .andExpect(jsonPath("$.numberOfUtility", Matchers.is(Matchers.equalTo(10))))
                .andExpect(jsonPath("$.numberOfFutility", Matchers.is(Matchers.equalTo(12))))
                .andExpect(jsonPath("$.checkedComment", Matchers.is(Matchers.equalTo(true))))
                .andReturn();
    }

    @Test
    public void deleteComment() throws Exception {
        commentService.addComment(new CommentDTO(1L, "Good job", LocalDateTime.now(), 10, 12, true, null));
        mvc.perform(delete("/api/v1/comment/{id}", 1L)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    public void updateComment() throws Exception {
        commentService.addComment(new CommentDTO(1L, "It is nice", LocalDateTime.now(), 11, 11, true, null));
        CommentDTO commentDTO = new CommentDTO(1L, "Good job", null, 10, 12, true, null);
        mvc.perform(put("/api/v1/comment/{id}", 1L)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(commentDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(Matchers.equalTo(1))))
                .andExpect(jsonPath("$.comment", Matchers.is(Matchers.equalTo("Good job"))))
                .andExpect(jsonPath("$.numberOfUtility", Matchers.is(Matchers.equalTo(10))))
                .andExpect(jsonPath("$.numberOfFutility", Matchers.is(Matchers.equalTo(12))))
                .andExpect(jsonPath("$.checkedComment", Matchers.is(Matchers.equalTo(true))))
                .andReturn();
    }
}
