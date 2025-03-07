package com.trendhive.backend.service;

import com.trendhive.backend.domain.Comment;
import com.trendhive.backend.domain.Trend;
import com.trendhive.backend.domain.User;
import com.trendhive.backend.dto.CommentResponseDTO;
import com.trendhive.backend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Comment addComment(User user, Trend trend, String content) {
        Comment comment = Comment.builder()
                .user(user)
                .trend(trend)
                .content(content)
                .build();
        return commentRepository.save(comment);
    }

    public List<CommentResponseDTO> getCommentsByTrend(Trend trend) {
        return commentRepository.findByTrend(trend).stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());
    }
}
