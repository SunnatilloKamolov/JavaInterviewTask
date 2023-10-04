package uz.job.javainterview.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import uz.job.javainterview.JavaInterviewApplication;
import uz.job.javainterview.dto.BlogDTO;
import uz.job.javainterview.service.BlogService;
import uz.job.javainterview.util.TestUtil;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = JavaInterviewApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class BlogControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    BlogController blogController;
    @Autowired
    BlogService blogService;

    @BeforeEach
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(blogController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }

    @Test
    public void should_ReturnAllBlogs() throws Exception {
        blogService.addBlog(new BlogDTO(1L, "Features of Java", "Multithreading", "It is very beneficial for structure", LocalDateTime.now(),
                10, false));
        mvc.perform(get("/api/v1/blogs")
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    void should_ReturnBlogById() throws Exception {
        blogService.addBlog(new BlogDTO(1L, "Features of Java", "Multithreading", "It is very beneficial for structure", LocalDateTime.now(),
                10, false));
        mvc.perform(get("/api/v1/blog/{id}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", Matchers.is(Matchers.equalTo("Features of Java"))))
                .andExpect(jsonPath("$.theme", Matchers.is(Matchers.equalTo("Multithreading"))))
                .andExpect(jsonPath("$.numberOfReadings", Matchers.is(Matchers.equalTo(10))))
                .andReturn();
    }

    @Test
    void createBlog() throws Exception {
        BlogDTO blogDTO = new BlogDTO(2L, "Features of Java", "Multithreading", "It is very beneficial for structure", null,
                10, false);
        mvc.perform(post("/api/v1/blog")
                        .contentType(APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(blogDTO)))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Matchers.is(Matchers.equalTo(1))))
                .andExpect(jsonPath("$.title", Matchers.is(Matchers.equalTo("Features of Java"))))
                .andExpect(jsonPath("$.numberOfReadings", Matchers.is(Matchers.equalTo(10))))
                .andExpect(jsonPath("$.checked", Matchers.is(Matchers.equalTo(false))))
                .andReturn();
    }

    @Test
    void deleteBlog() throws Exception {
        blogService.addBlog(new BlogDTO(1L, "Features of Java", "Multithreading", "It is very beneficial for structure", LocalDateTime.now(),
                10, false));
        mvc.perform(delete("/api/v1/blog/{id}", 1L)
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(status().is(200))
                .andDo(print());
    }

    @Test
    void updateBlog() throws Exception {
        blogService.addBlog(new BlogDTO(1L, "Features of Java", "Multithreading", "It is very beneficial for structure", null,
                10, false));
        BlogDTO blogDTO = new BlogDTO(1L, "Features of Java", "Encapsulation", "It is simple", null,
                15, false);
        mvc.perform(put("/api/v1/blog/{id}", 1L)
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(blogDTO)))
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", Matchers.is(Matchers.equalTo(1))))
                .andExpect(jsonPath("$.title", Matchers.is(Matchers.equalTo("Features of Java"))))
                .andExpect(jsonPath("$.text", Matchers.is(Matchers.equalTo("It is simple"))))
                .andExpect(jsonPath("$.numberOfReadings", Matchers.is(Matchers.equalTo(15))))
                .andExpect(jsonPath("$.checked", Matchers.is(Matchers.equalTo(false))))
                .andReturn();
    }
}