package uz.job.javainterview.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.job.javainterview.dto.BlogDTO;
import uz.job.javainterview.exception.EntityNotFoundException;
import uz.job.javainterview.mapper.BlogMapper;
import uz.job.javainterview.model.Blog;
import uz.job.javainterview.service.BlogService;
import uz.job.javainterview.util.Util;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BlogController {
    @Autowired
    BlogService blogService;
    private final Logger logger = LoggerFactory.getLogger(BlogController.class);

    @GetMapping(value ={ "/api/v1/blogs"})
    ResponseEntity<List<BlogDTO>> getAll(Model model) {
        List<Blog> blogs = blogService.getAll();
        List<BlogDTO> blogDTOS = new ArrayList<>();
        for (Blog blog : blogs) {
            blogDTOS.add(BlogMapper.toDTO(blog));
        }
        if (blogDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            model.addAttribute("blog",blogs);
            return ResponseEntity.ok(blogDTOS);
        }
    }
    @GetMapping("/")
    public String viewBlogs(Model model){
        model.addAttribute("blogs",blogService.getAll());
        return "index";
    }
    @GetMapping("/showNewBlogForm")
    public String showNewBlogForm(Model model){
        Blog blog=new Blog();
        model.addAttribute("blog",blog);
        return "new_blog";
    }
    @GetMapping("/api/v1/blog/{id}")
    ResponseEntity<BlogDTO> getBlogById(@PathVariable Long id) throws EntityNotFoundException {
        BlogDTO blogDTO = BlogMapper.toDTO(blogService.getBlogById(id));
        return ResponseEntity.ok(blogDTO);
    }


    @PostMapping("/api/v1/blog")
    ResponseEntity<BlogDTO> addBlog(@RequestBody BlogDTO blogDTO) {
        try {
            return ResponseEntity.ok(BlogMapper.toDTO(blogService.addBlog(blogDTO)));
        } catch (Exception e) {
            logger.error("Problem is happened" + Util.getMethodName(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
    @PostMapping("saveBlog")
    public String saveBlog(@ModelAttribute BlogDTO blogDTO){
        blogService.addBlog(blogDTO);
        return "redirect:/";
    }

    @DeleteMapping("/api/v1/blog/{id}")
    ResponseEntity<Void> deleteBlog(@PathVariable Long id) throws EntityNotFoundException {
        try {
            blogService.deleteBlog(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Problem is happened" + Util.getMethodName(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/api/v1/blog/{id}")
    ResponseEntity<BlogDTO> updateBlog(@PathVariable Long id, @RequestBody BlogDTO blogDTO) throws EntityNotFoundException {
        try {
            return ResponseEntity.ok(BlogMapper.toDTO(blogService.updateBlog(id, blogDTO)));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Problem is happened" + Util.getMethodName(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
