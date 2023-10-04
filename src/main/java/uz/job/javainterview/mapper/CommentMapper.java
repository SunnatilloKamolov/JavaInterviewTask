package uz.job.javainterview.mapper;


import uz.job.javainterview.dto.BlogDTO;
import uz.job.javainterview.dto.CommentDTO;
import uz.job.javainterview.model.Blog;
import uz.job.javainterview.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static CommentDTO toDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setComment(comment.getComment());
        dto.setCreatedCommentDate(comment.getCreatedCommentDate());
        dto.setNumberOfUtility(comment.getNumberOfUtility());
        dto.setNumberOfFutility(comment.getNumberOfFutility());
        dto.setCheckedComment(comment.getCheckedComment());
        dto.setBlog(comment.getBlog());
        return dto;
    }

    public static Comment toModel(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setComment(commentDTO.getComment());
        comment.setCreatedCommentDate(commentDTO.getCreatedCommentDate());
        comment.setNumberOfUtility(commentDTO.getNumberOfUtility());
        comment.setNumberOfFutility(commentDTO.getNumberOfFutility());
        comment.setCheckedComment(commentDTO.getCheckedComment());

        comment.setBlog(commentDTO.getBlog());
        return comment;
    }
}
