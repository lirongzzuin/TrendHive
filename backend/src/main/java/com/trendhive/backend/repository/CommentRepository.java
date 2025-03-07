package com.trendhive.backend.repository;

import com.trendhive.backend.domain.Comment;
import com.trendhive.backend.domain.Trend;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTrend(Trend trend);
}
