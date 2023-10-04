package uz.job.javainterview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.job.javainterview.dto.CommentDTO;
import uz.job.javainterview.exception.EntityNotFoundException;
import uz.job.javainterview.mapper.CommentMapper;
import uz.job.javainterview.model.Comment;
import uz.job.javainterview.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }
    public List<Comment>getUncheckedComments(){
       List<Comment> comments=commentRepository.findAll();
        List<Comment>comments1=new ArrayList<>();
       for (Comment comment:comments) {
            if (!comment.getCheckedComment()){
                comments1.add(comment);
            }
       }return comments1;
    }

    public Comment getCommentById(Long id) throws EntityNotFoundException {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty()) {
            throw new EntityNotFoundException("Comment not found with id=" + id);
        }
        return optionalComment.get();
    }

    public Comment addComment(CommentDTO commentDTO) {
        return commentRepository.save(CommentMapper.toModel(commentDTO));
    }

    public void deleteComment(Long id) throws EntityNotFoundException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id=" + id));
        commentRepository.delete(comment);
    }

    public Comment updateComment(Long id, CommentDTO commentDTO) throws EntityNotFoundException {
        Comment existComment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id=" + id));
        Comment updateComment = CommentMapper.toModel(commentDTO);
        updateComment.setId(existComment.getId());
        return commentRepository.save(updateComment);
    }
}
