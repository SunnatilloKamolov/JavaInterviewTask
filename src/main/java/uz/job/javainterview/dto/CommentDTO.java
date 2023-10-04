package uz.job.javainterview.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.job.javainterview.model.Blog;

import java.time.LocalDateTime;
import java.util.List;

import static uz.job.javainterview.constant.AppConstant.DATETIME_FORMAT;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT)
    private LocalDateTime createdCommentDate;
    private int numberOfUtility;
    private int numberOfFutility;
    private Boolean checkedComment;
    private Blog blog;
}
