package uz.job.javainterview.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static uz.job.javainterview.constant.AppConstant.DATETIME_FORMAT;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {
    private Long id;
    private String title;
    private String theme;
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATETIME_FORMAT)
    private LocalDateTime createdDate;
    private int numberOfReadings;
    private boolean checked;

}
