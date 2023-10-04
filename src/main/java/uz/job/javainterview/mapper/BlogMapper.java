package uz.job.javainterview.mapper;


import uz.job.javainterview.dto.BlogDTO;
import uz.job.javainterview.model.Blog;

import java.util.ArrayList;

public class BlogMapper {

    public static BlogDTO toDTO(Blog blog) {
        BlogDTO dto = new BlogDTO();
        dto.setId(blog.getId());
        dto.setTitle(blog.getTitle());
        dto.setTheme(blog.getTheme());
        dto.setText(blog.getText());
        dto.setCreatedDate(blog.getCreatedDate());
        dto.setNumberOfReadings(blog.getNumberOfReadings());
        dto.setChecked(blog.isChecked());
        return dto;
    }

    public static ArrayList<BlogDTO> toDTO(ArrayList<Blog> blogs) {
        ArrayList<BlogDTO> blogDTOS = new ArrayList<>();
        for (Blog blog : blogs) {
            blogDTOS.add(toDTO(blog));
        }
        return blogDTOS;
    }

    public static Blog toModel(BlogDTO blogDTO) {
    Blog blog=new Blog();
    blog.setId(blogDTO.getId());
    blog.setTitle(blogDTO.getTitle());
    blog.setTheme(blogDTO.getTheme());
    blog.setText(blogDTO.getText());
    blog.setCreatedDate(blogDTO.getCreatedDate());
    blog.setNumberOfReadings(blogDTO.getNumberOfReadings());
    blog.setChecked(blogDTO.isChecked());
    return blog;
    }
}
