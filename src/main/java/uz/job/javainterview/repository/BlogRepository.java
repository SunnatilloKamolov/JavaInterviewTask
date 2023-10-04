package uz.job.javainterview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.job.javainterview.model.Blog;
@Repository
public interface BlogRepository extends JpaRepository<Blog,Long> {
}
