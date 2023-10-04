package uz.job.javainterview.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.job.javainterview.dto.CommentDTO;
import uz.job.javainterview.exception.EntityNotFoundException;
import uz.job.javainterview.mapper.CommentMapper;
import uz.job.javainterview.model.Comment;
import uz.job.javainterview.service.CommentService;
import uz.job.javainterview.util.Util;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;
    private final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @GetMapping("/api/v1/comments")
    ResponseEntity<List<CommentDTO>> getAll() {
        List<Comment> comments = commentService.getAll();
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : comments) {
            commentDTOS.add(CommentMapper.toDTO(comment));
        }
        if (commentDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(commentDTOS);
        }
    }

    @GetMapping("/api/v1/UncheckedComments")
    ResponseEntity<List<CommentDTO>> getUncheckedComments() {
        List<Comment> uncheckedComments = commentService.getUncheckedComments();
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : uncheckedComments) {
            commentDTOS.add(CommentMapper.toDTO(comment));
        }
        if (commentDTOS.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(commentDTOS);
        }
    }

    @GetMapping("/api/v1/comment/{id}")
    ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) throws EntityNotFoundException {
        CommentDTO commentDTO = CommentMapper.toDTO(commentService.getCommentById(id));
        return ResponseEntity.ok(commentDTO);
    }

    @PostMapping("/api/v1/comment")
    ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO commentDTO) {
        try {
            return ResponseEntity.ok(CommentMapper.toDTO(commentService.addComment(commentDTO)));
        } catch (Exception e) {
            logger.error("Problem is happened" + Util.getMethodName(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/api/v1/comment/{id}")
    ResponseEntity<Void> deleteComment(@PathVariable Long id) throws EntityNotFoundException {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Problem is happened" + Util.getMethodName(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/api/v1/comment/{id}")
    ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) throws EntityNotFoundException {
        try {
            return ResponseEntity.ok(CommentMapper.toDTO(commentService.updateComment(id, commentDTO)));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Problem is happened" + Util.getMethodName(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
