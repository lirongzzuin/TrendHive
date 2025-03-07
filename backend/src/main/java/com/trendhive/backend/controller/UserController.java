package com.trendhive.backend.controller;

import com.trendhive.backend.domain.User;
import com.trendhive.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestParam String username,
                                             @RequestParam String email,
                                             @RequestParam String password) {
        User user = userService.registerUser(username, email, password);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/login")  // ✅ 이 부분이 존재해야 함!
    public ResponseEntity<User> loginUser(@RequestParam String username,
                                          @RequestParam String password) {
        return userService.findByUsername(username)
                .filter(user -> user.getPassword().equals(password)) // 비밀번호 검증 (추후 해싱 적용 필요)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
