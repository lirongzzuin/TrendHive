package com.trendhive.backend.controller;

import com.trendhive.backend.domain.Trend;
import com.trendhive.backend.service.TrendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trends")
@RequiredArgsConstructor
public class TrendController {
    private final TrendService trendService;

    @PostMapping("/add")
    public ResponseEntity<Trend> addTrend(@RequestParam String title,
                                          @RequestParam String description,
                                          @RequestParam String sourceUrl) {
        Trend trend = trendService.addTrend(title, description, sourceUrl);
        return ResponseEntity.ok(trend);
    }

    @GetMapping
    public ResponseEntity<List<Trend>> getAllTrends() {
        return ResponseEntity.ok(trendService.getAllTrends());
    }
}
