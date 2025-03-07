package com.trendhive.backend.service;

import com.trendhive.backend.domain.Trend;
import com.trendhive.backend.repository.TrendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrendService {
    private final TrendRepository trendRepository;

    @Transactional
    public Trend addTrend(String title, String description, String sourceUrl) {
        Trend trend = Trend.builder()
                .title(title)
                .description(description)
                .sourceUrl(sourceUrl)
                .build();
        return trendRepository.save(trend);
    }

    public List<Trend> getAllTrends() {
        return trendRepository.findAll();
    }
}
