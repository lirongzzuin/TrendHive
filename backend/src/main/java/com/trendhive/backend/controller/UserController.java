package com.trendhive.backend.controller;

import com.trendhive.backend.domain.User;
import com.trendhive.backend.dto.UserResponseDTO;
import com.trendhive.backend.service.UserService;
import com.trendhive.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestParam String username,
                                                        @RequestParam String email,
                                                        @RequestParam String password) {
        User user = userService.registerUser(username, email, password);
        UserResponseDTO responseDTO = new UserResponseDTO(
                user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt()
        );
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username,
                                       @RequestParam String password) {
        return userService.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword())) // 🛑 여기서 검증!
                .map(user -> {
                    String token = jwtUtil.generateToken(username);
                    return ResponseEntity.ok(Collections.singletonMap("token", token));
                })
                .orElseGet(() -> ResponseEntity.status(401).body(Collections.singletonMap("error", "Invalid credentials")));
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(
            @PathVariable String username,
            @RequestHeader("Authorization") String token) {
        // Bearer Token 검증
        if (!jwtUtil.validateToken(token.replace("Bearer ", ""))) {
            return ResponseEntity.status(401).body(null);
        }

        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
