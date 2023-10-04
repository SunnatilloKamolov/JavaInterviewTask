package uz.job.javainterview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.job.javainterview.dto.BlogDTO;
import uz.job.javainterview.exception.EntityNotFoundException;
import uz.job.javainterview.mapper.BlogMapper;
import uz.job.javainterview.model.Blog;
import uz.job.javainterview.repository.BlogRepository;

import java.util.List;
import java.util.Optional;
@Service
public class BlogService {
    @Autowired
    BlogRepository blogRepository;
    public List<Blog>getAll(){
        return blogRepository.findAll();
    }
    public Blog getBlogById(Long id) throws EntityNotFoundException{
        Optional<Blog> optionalBlog = blogRepository.findById(id);
        if (optionalBlog.isEmpty()){
            throw new EntityNotFoundException("Blog not found with id="+id);
        }
        return optionalBlog.get();
    }
    public Blog addBlog(BlogDTO blogDTO){
        return blogRepository.save(BlogMapper.toModel(blogDTO));
    }
    public void deleteBlog(Long id) throws EntityNotFoundException{
        Blog blog=blogRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Blog not found with id="+id));
        blogRepository.delete(blog);
    }
    public Blog updateBlog(Long id,BlogDTO blogDTO) throws EntityNotFoundException{
        Blog existBlog=blogRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Blog not found with id="+id));
        Blog updateBlog=BlogMapper.toModel(blogDTO);
        updateBlog.setId(existBlog.getId());
        return blogRepository.save(updateBlog);
    }
}
