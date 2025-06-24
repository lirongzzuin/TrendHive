package com.trendhive.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trendhive.backend.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired UserRepository userRepository;

    @Test
    @DisplayName("회원가입 성공")
    void registerUser_success() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .param("username", "testuser")
                        .param("email", "test@example.com")
                        .param("password", "1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    @DisplayName("로그인 성공 후 JWT 토큰 발급")
    void loginUser_success() throws Exception {
        // 먼저 회원가입
        mockMvc.perform(post("/api/users/register")
                        .param("username", "testuser2")
                        .param("email", "test2@example.com")
                        .param("password", "1234"))
                .andExpect(status().isOk());

        // 로그인
        mockMvc.perform(post("/api/users/login")
                        .param("username", "testuser2")
                        .param("password", "1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
}
