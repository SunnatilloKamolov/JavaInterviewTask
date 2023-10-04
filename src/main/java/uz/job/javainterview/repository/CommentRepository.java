package uz.job.javainterview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.job.javainterview.model.Comment;
@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
