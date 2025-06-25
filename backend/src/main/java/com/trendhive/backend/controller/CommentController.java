package com.trendhive.backend.controller;

import com.trendhive.backend.domain.Comment;
import com.trendhive.backend.domain.Trend;
import com.trendhive.backend.domain.User;
import com.trendhive.backend.dto.CommentResponseDTO;
import com.trendhive.backend.service.CommentService;
import com.trendhive.backend.service.TrendService;
import com.trendhive.backend.service.UserService;
import com.trendhive.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final TrendService trendService;
    private final JwtUtil jwtUtil;

    /**
     * 🔹 댓글 추가 (JWT 인증 필요)
     */
    @PostMapping("/add")
    public ResponseEntity<Comment> addComment(@RequestHeader("Authorization") String token,
                                              @RequestParam Long trendId,
                                              @RequestParam String content) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Trend trend = trendService.findTrendEntityById(trendId)
                .orElseThrow(() -> new RuntimeException("Trend not found"));

        Comment comment = commentService.addComment(user, trend, content);
        return ResponseEntity.ok(comment);
    }


    /**
     * 🔹 특정 트렌드의 댓글 조회
     */
    @GetMapping("/{trendId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByTrend(@PathVariable Long trendId) {
        Trend trend = trendService.findTrendEntityById(trendId)
                .orElseThrow(() -> new RuntimeException("Trend not found"));

        List<CommentResponseDTO> comments = commentService.getCommentsByTrend(trend);
        return ResponseEntity.ok(comments);
    }
}
