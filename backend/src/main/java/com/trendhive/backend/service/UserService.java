package com.trendhive.backend.service;

import com.trendhive.backend.domain.User;
import com.trendhive.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(String username, String email, String password) {
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password)) // 🛑 비밀번호 암호화 필수!
                .createdAt(LocalDateTime.now())
                .build();
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {  // 🔹 Optional로 반환
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
