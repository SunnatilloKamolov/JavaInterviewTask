package uz.job.javainterview.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 400)
    private String comment;

    private LocalDateTime createdCommentDate;
    @Column(nullable = false)
    private int numberOfUtility;
    @Column(nullable = false)
    private int numberOfFutility;
    private Boolean checkedComment;
    @OneToOne(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinTable(name = "blog_comment",joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns =@JoinColumn(name = "blog_id") )
    private Blog blog;
}
