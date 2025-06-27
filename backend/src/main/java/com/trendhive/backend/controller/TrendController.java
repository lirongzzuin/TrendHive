package com.trendhive.backend.controller;

import com.trendhive.backend.dto.TrendRequestDTO;
import com.trendhive.backend.dto.TrendResponseDTO;
import com.trendhive.backend.service.TrendService;
import com.trendhive.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trends")
@RequiredArgsConstructor
public class TrendController {
    private final TrendService trendService;
    private final JwtUtil jwtUtil;

    /**
     * 트렌드 추가 (JWT 인증 필요)
     */
    @PostMapping("/add")
    public ResponseEntity<TrendResponseDTO> addTrend(
            @RequestBody TrendRequestDTO requestDTO,
            @RequestHeader("Authorization") String token) {

        // 1️⃣ Bearer 토큰 추출 및 검증
        String jwtToken = token.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(jwtToken);

        // 2️⃣ 트렌드 추가 (로그인한 사용자 자동 적용)
        TrendResponseDTO response = trendService.addTrend(
                requestDTO.getTitle(),
                requestDTO.getDescription(),
                requestDTO.getCategory(),
                requestDTO.getSourceUrl(),
                username // `createdBy`를 JWT에서 추출한 `username`으로 설정
        );
        return ResponseEntity.ok(response);
    }

    /**
     * 모든 트렌드 조회 (공개 API)
     */
    @GetMapping
    public ResponseEntity<List<TrendResponseDTO>> getAllTrends(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(trendService.getAllTrends(page, size));
    }

    @GetMapping("/{trendId}")
    public ResponseEntity<?> getTrendById(@PathVariable Long trendId) {
        return trendService.getTrendById(trendId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
