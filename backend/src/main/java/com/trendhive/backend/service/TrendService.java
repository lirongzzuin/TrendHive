package com.trendhive.backend.service;

import com.trendhive.backend.domain.Trend;
import com.trendhive.backend.domain.User;
import com.trendhive.backend.dto.TrendResponseDTO;
import com.trendhive.backend.repository.TrendRepository;
import com.trendhive.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrendService {
    private final TrendRepository trendRepository;
    private final UserRepository userRepository;

    /**
     * 🔹 트렌드 추가 (JWT 인증된 사용자)
     */
    @Transactional
    public TrendResponseDTO addTrend(String title, String description, String category, String createdByUsername) {
        // 1️⃣ JWT에서 추출한 username으로 User 조회
        User user = userRepository.findByUsername(createdByUsername)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ 트렌드 생성 및 저장
        Trend trend = Trend.builder()
                .title(title)
                .description(description)
                .category(category)
                .createdBy(user) // 🔥 User 객체 직접 설정
                .build();

        trendRepository.save(trend);
        return new TrendResponseDTO(trend);
    }

    /**
     * 🔹 모든 트렌드 조회
     */
    public List<TrendResponseDTO> getAllTrends() {
        return trendRepository.findAll().stream()
                .map(TrendResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<TrendResponseDTO> getTrendById(Long id) {
        return trendRepository.findById(id).map(TrendResponseDTO::new); // 🔹 Trend → TrendResponseDTO 변환
    }

    public Optional<Trend> findTrendEntityById(Long id) {
        return trendRepository.findById(id);
    }

    public Optional<Trend> findById(Long id) {
        return trendRepository.findById(id);
    }
}
