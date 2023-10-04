package uz.job.javainterview.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import uz.job.javainterview.exception.EntityNotFoundException;
import uz.job.javainterview.mapper.BlogMapper;
import uz.job.javainterview.model.Blog;
import uz.job.javainterview.repository.BlogRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {
    @InjectMocks
    BlogService blogService;
    @Mock
    BlogRepository blogRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getBlogs() {
        List<Blog> blogs = new ArrayList<>();
        Blog blog1 = new Blog(1L, "Features of Java", "Inheritance", "It is very beneficial for this", null,
                10, false);
        Blog blog2 = new Blog(2L, "Features of Inheritance", "Abstraction", "It is simple", LocalDateTime.now(),
                11, true);
        blogs.add(blog1);
        blogs.add(blog2);
        when(blogRepository.findAll()).thenReturn(blogs);
        List<Blog> blogList = blogService.getAll();
        assertNotNull(blogList);
        assertEquals(2, blogs.size());
        assertEquals(1L, blogList.get(0).getId());
        assertEquals(2L, blogList.get(1).getId());
        assertEquals("Features of Java", blogList.get(0).getTitle());
        assertNull(blogList.get(0).getCreatedDate());
        assertFalse(blogList.get(0).isChecked());
        assertEquals("Abstraction", blogList.get(1).getTheme());
        assertEquals(11, blogList.get(1).getNumberOfReadings());
    }

    @Test
    void getBlogById() throws EntityNotFoundException {
        Optional<Blog> optionalBlog = Optional.of(new Blog(1L, "Features of Java", "Inheritance", "It is very beneficial for this", null,
                10, false));
        when(blogRepository.findById(1L)).thenReturn(optionalBlog);
        Blog blog = blogService.getBlogById(1L);
        assertNotNull(blog);
        assertEquals("Inheritance", blog.getTheme());
        assertNull(blog.getTheme());
        assertEquals(10, blog.getNumberOfReadings());
        assertFalse(blog.isChecked());
    }

    @Test
    void addBlog() {
        Blog blog1 = new Blog(1L, "Features of Inheritance", "Abstraction", "It is simple", LocalDateTime.now(),
                11, true);
        Blog blog2 = new Blog(2L, "Features of Java", "Inheritance", "It is very beneficial for this", null,
                10, false);
        var dto1 = BlogMapper.toDTO(blog1);
        var dto2 = BlogMapper.toDTO(blog2);
        blogService.addBlog(dto1);
        blogService.addBlog(dto2);
        verify(blogRepository, times(2)).save(any());
    }

    @Test
    void deleteBlog() throws EntityNotFoundException {
        Blog blog1 = new Blog(1L, "Features of Inheritance", "Abstraction", "It is simple", LocalDateTime.now(),
                11, true);
        when(blogRepository.findById(1L)).thenReturn(Optional.of(blog1));
        blogService.deleteBlog(1L);
        verify(blogRepository, times(1)).delete(any());
    }

    @Test
    void updateBlog() throws Exception {
        Blog blog1 = new Blog(1L, "Features of Inheritance", "Abstraction", "It is simple", LocalDateTime.now(),
                11, true);
        var dto = BlogMapper.toDTO(blog1);
        when(blogRepository.findById(anyLong())).thenReturn(Optional.of(blog1));
        when(blogRepository.save(any(Blog.class))).thenReturn(blog1);
        blog1.setTitle("Multithreading");
        blog1.setNumberOfReadings(12);
        Blog existBlog = blogService.updateBlog(1L, dto);
        assertNotNull(existBlog);
        assertEquals("Multithreading", blog1.getTitle());
        assertEquals(12, blog1.getNumberOfReadings());
        assertEquals("Abstraction", blog1.getTheme());
    }
}