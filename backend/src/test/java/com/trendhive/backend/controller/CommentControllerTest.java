package com.trendhive.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendhive.backend.domain.Trend;
import com.trendhive.backend.domain.User;
import com.trendhive.backend.dto.CommentResponseDTO;
import com.trendhive.backend.repository.CommentRepository;
import com.trendhive.backend.repository.TrendRepository;
import com.trendhive.backend.repository.UserRepository;
import com.trendhive.backend.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CommentControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired UserRepository userRepository;
    @Autowired TrendRepository trendRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired JwtUtil jwtUtil;

    private User savedUser;
    private Trend savedTrend;

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        trendRepository.deleteAll();
        userRepository.deleteAll();

        savedUser = userRepository.save(User.builder()
                .username("commenter")
                .email("commenter@example.com")
                .password("encoded")
                .build());

        savedTrend = trendRepository.save(Trend.builder()
                .title("테스트 트렌드")
                .description("설명")
                .category("기타")
                .createdBy(savedUser)
                .build());
    }

    @Test
    @DisplayName("JWT 없이 댓글 등록 시 401 Unauthorized")
    void addComment_withoutToken_shouldFail() throws Exception {
        mockMvc.perform(post("/api/comments/add")
                        .param("trendId", savedTrend.getId().toString())
                        .param("content", "댓글 내용"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("JWT 포함 시 댓글 등록 성공")
    void addComment_withToken_shouldSucceed() throws Exception {
        String token = jwtUtil.generateToken(savedUser.getUsername());

        mockMvc.perform(post("/api/comments/add")
                        .header("Authorization", "Bearer " + token)
                        .param("trendId", savedTrend.getId().toString())
                        .param("content", "첫 번째 댓글"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("첫 번째 댓글"))
                .andExpect(jsonPath("$.user.username").value("commenter"));

        assertThat(commentRepository.findByTrend(savedTrend)).hasSize(1);
    }

    @Test
    @DisplayName("트렌드에 등록된 댓글 조회")
    void getCommentsByTrend_shouldReturnList() throws Exception {
        // 댓글 직접 등록
        commentRepository.saveAll(List.of(
                savedUser.comment(savedTrend, "댓글 1"),
                savedUser.comment(savedTrend, "댓글 2")
        ));

        mockMvc.perform(get("/api/comments/" + savedTrend.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].content").value("댓글 1"));
    }
}
