package uz.job.javainterview.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.job.javainterview.dto.CommentDTO;
import uz.job.javainterview.exception.EntityNotFoundException;
import uz.job.javainterview.mapper.CommentMapper;
import uz.job.javainterview.model.Comment;
import uz.job.javainterview.repository.CommentRepository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @InjectMocks
    CommentService commentService;
    @Mock
    CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getComments() {
        List<Comment> comments = new ArrayList<>();
        Comment comment1 = new Comment(1L, "Good job", LocalDateTime.now(), 10, 12, true, null);
        Comment comment2 = new Comment(2L, "Well done", null, 5, 1, false, null);
        comments.add(comment1);
        comments.add(comment2);

        when(commentRepository.findAll()).thenReturn(comments);
        List<Comment> commentList = commentService.getAll();
        assertNotNull(commentList);
        assertEquals(2, comments.size());
        assertEquals("Good job", commentList.get(0).getComment());
        assertEquals(12, commentList.get(0).getNumberOfFutility());
        assertEquals(true, commentList.get(0).getCheckedComment());
        assertEquals("Well done", commentList.get(1).getComment());
        assertEquals(false, commentList.get(1).getCheckedComment());
    }

    @Test
    void getCommentById() throws EntityNotFoundException {
        Optional<Comment> optionalComment = Optional.of(new Comment(1L, "Good job", LocalDateTime.now(), 10, 12,
                true, null));
        when(commentRepository.findById(1L)).thenReturn(optionalComment);
        Comment comment = commentService.getCommentById(1L);
        assertNotNull(comment);
        assertEquals("Good job", comment.getComment());
        assertEquals(10, comment.getNumberOfUtility());
        assertEquals(12, comment.getNumberOfFutility());
        assertEquals(true, comment.getCheckedComment());
    }

    @Test
    void addComment() {
        Comment comment1 = new Comment(1L, "Good job", LocalDateTime.now(), 10, 12, true, null);
        Comment comment2 = new Comment(2L, "Well done", null, 5, 1, false, null);
        var dto1 = CommentMapper.toDTO(comment1);
        var dto2 = CommentMapper.toDTO(comment2);
        commentService.addComment(dto1);
        commentService.addComment(dto2);
        verify(commentRepository, times(2)).save(any());
    }

    @Test
    void deleteComment() throws EntityNotFoundException {
        Comment comment1 = new Comment(1L, "Good job", LocalDateTime.now(), 10, 12, true, null);
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment1));
        commentService.deleteComment(1L);
        verify(commentRepository, times(1)).delete(any());
    }

    @Test
    void updateComment() throws EntityNotFoundException {
        Comment comment1 = new Comment(1L, "Good job", LocalDateTime.now(), 10, 12, true, null);
        var dto = CommentMapper.toDTO(comment1);
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment1));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment1);
        comment1.setComment("Bravo!");
        comment1.setCheckedComment(false);
        Comment existingComment = commentService.updateComment(1L, dto);
        assertNotNull(existingComment);
        assertEquals("Bravo!", comment1.getComment());
        assertEquals(1L, comment1.getId());
        assertEquals(false, comment1.getCheckedComment());
        assertEquals(10, comment1.getNumberOfUtility());
    }
}
