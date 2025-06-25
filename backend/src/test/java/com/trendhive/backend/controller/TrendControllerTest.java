package com.trendhive.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendhive.backend.domain.User;
import com.trendhive.backend.dto.TrendRequestDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TrendControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired UserRepository userRepository;
    @Autowired TrendRepository trendRepository;
    @Autowired JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        trendRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("JWT 없이 트렌드 등록 시 401 에러")
    void addTrend_unauthorized() throws Exception {
        TrendRequestDTO dto = new TrendRequestDTO();
        dto.setTitle("트렌드 제목");
        dto.setDescription("트렌드 설명");
        dto.setCategory("정치");

        mockMvc.perform(post("/api/trends/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("JWT 포함 시 트렌드 등록 성공")
    void addTrend_success_withJWT() throws Exception {
        // 1️⃣ 사용자 등록
        User user = User.builder()
                .username("trenduser")
                .email("trenduser@example.com")
                .password("encoded") // 테스트에서는 인코딩 여부 상관 없음
                .build();
        userRepository.save(user);

        // 2️⃣ JWT 생성
        String token = jwtUtil.generateToken(user.getUsername());

        // 3️⃣ 요청 객체
        TrendRequestDTO dto = new TrendRequestDTO();
        dto.setTitle("트렌드 제목");
        dto.setDescription("트렌드 설명");
        dto.setCategory("정치");

        // 4️⃣ 요청 및 검증
        mockMvc.perform(post("/api/trends/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("트렌드 제목"))
                .andExpect(jsonPath("$.category").value("정치"))
                .andExpect(jsonPath("$.createdBy").value("trenduser"));

        // 5️⃣ 실제 DB에 저장됐는지 확인
        assertThat(trendRepository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("모든 트렌드 조회")
    void getAllTrends_shouldReturnList() throws Exception {
        // Given: 사용자 및 트렌드 2개 생성
        User user = userRepository.save(User.builder()
                .username("viewer")
                .email("viewer@example.com")
                .password("pw")
                .build());

        trendRepository.saveAll(java.util.List.of(
                com.trendhive.backend.domain.Trend.builder()
                        .title("트렌드1")
                        .description("설명1")
                        .category("카테고리1")
                        .createdBy(user)
                        .build(),
                com.trendhive.backend.domain.Trend.builder()
                        .title("트렌드2")
                        .description("설명2")
                        .category("카테고리2")
                        .createdBy(user)
                        .build()
        ));

        // When & Then
        mockMvc.perform(get("/api/trends/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("트렌드1"));
    }
}
