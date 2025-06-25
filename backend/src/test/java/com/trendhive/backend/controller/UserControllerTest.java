package com.trendhive.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendhive.backend.domain.User;
import com.trendhive.backend.repository.UserRepository;
import com.trendhive.backend.repository.TrendRepository;
import com.trendhive.backend.repository.CommentRepository;
import org.springframework.transaction.annotation.Transactional;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired UserRepository userRepository;
    @Autowired TrendRepository trendRepository;
    @Autowired CommentRepository commentRepository;

    @BeforeEach
    void clean() {
        commentRepository.deleteAll();
        trendRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void registerUser_success() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .param("username", "testuser")
                        .param("email", "test@example.com")
                        .param("password", "1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.createdAt").exists());

        // DB에 저장됐는지 확인
        User savedUser = userRepository.findByUsername("testuser").orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("로그인 성공 후 JWT 토큰 발급")
    void loginUser_success() throws Exception {
        // Given: 회원가입 선행
        mockMvc.perform(post("/api/users/register")
                        .param("username", "testuser2")
                        .param("email", "test2@example.com")
                        .param("password", "1234"))
                .andExpect(status().isOk());

        // When & Then: 로그인
        mockMvc.perform(post("/api/users/login")
                        .param("username", "testuser2")
                        .param("password", "1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인 시 실패")
    void loginUser_withWrongPassword_shouldFail() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .param("username", "wrongpassuser")
                        .param("email", "wrongpass@example.com")
                        .param("password", "correctpw"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/users/login")
                        .param("username", "wrongpassuser")
                        .param("password", "wrongpw"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid credentials"));
    }

    @Test
    @DisplayName("존재하지 않는 사용자로 로그인 시 실패")
    void loginUser_withNonexistentUser_shouldFail() throws Exception {
        mockMvc.perform(post("/api/users/login")
                        .param("username", "notexistuser")
                        .param("password", "any"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid credentials"));
    }

    @Test
    @DisplayName("중복된 사용자 이름으로 회원가입 시 실패")
    void registerUser_withDuplicateUsername_shouldFail() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .param("username", "dupuser")
                        .param("email", "dup@example.com")
                        .param("password", "pw"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/users/register")
                        .param("username", "dupuser")
                        .param("email", "another@example.com")
                        .param("password", "pw"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("빈 사용자 이름으로 회원가입 시 실패")
    void registerUser_withEmptyUsername_shouldFail() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .param("username", "")
                        .param("email", "empty@example.com")
                        .param("password", "1234"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유효하지 않은 이메일로 회원가입 시 실패")
    void registerUser_withInvalidEmail_shouldFail() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .param("username", "invalidEmailUser")
                        .param("email", "not-an-email")
                        .param("password", "1234"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("너무 짧은 비밀번호로 회원가입 시 실패")
    void registerUser_withShortPassword_shouldFail() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .param("username", "shortpw")
                        .param("email", "shortpw@example.com")
                        .param("password", "1"))
                .andExpect(status().isBadRequest());
    }
}
