package com.trendhive.backend.repository;

import com.trendhive.backend.domain.Trend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrendRepository extends JpaRepository<Trend, Long> {
}
