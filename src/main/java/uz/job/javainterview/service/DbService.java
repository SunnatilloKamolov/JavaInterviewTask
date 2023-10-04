package uz.job.javainterview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.job.javainterview.dto.BlogDTO;
import uz.job.javainterview.dto.CommentDTO;
import uz.job.javainterview.mapper.BlogMapper;
import uz.job.javainterview.model.Blog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DbService {
    @Autowired
    BlogService blogService;
    @Autowired
    CommentService commentService;
    public void initTestData() throws Exception{

        blogService.addBlog(new BlogDTO(1L,"Features of Java","Multithreading","It is very beneficial for structure",LocalDateTime.now(),
                10,false));
        commentService.addComment(new CommentDTO(1L,"Good job", LocalDateTime.now(),10,12,true,null));

    }
}
