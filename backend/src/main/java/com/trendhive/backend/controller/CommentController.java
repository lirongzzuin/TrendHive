package com.trendhive.backend.controller;

import com.trendhive.backend.domain.Comment;
import com.trendhive.backend.domain.Trend;
import com.trendhive.backend.domain.User;
import com.trendhive.backend.service.CommentService;
import com.trendhive.backend.service.TrendService;
import com.trendhive.backend.service.UserService;
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

    @PostMapping("/add")
    public ResponseEntity<Comment> addComment(@RequestParam String username,
                                              @RequestParam Long trendId,
                                              @RequestParam String content) {
        User user = userService.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Trend trend = trendService.getAllTrends().stream()
                .filter(t -> t.getId().equals(trendId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Trend not found"));

        Comment comment = commentService.addComment(user, trend, content);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/{trendId}")
    public ResponseEntity<List<Comment>> getCommentsByTrend(@PathVariable Long trendId) {
        Trend trend = trendService.getAllTrends().stream()
                .filter(t -> t.getId().equals(trendId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Trend not found"));

        return ResponseEntity.ok(commentService.getCommentsByTrend(trend));
    }
}
