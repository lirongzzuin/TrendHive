package com.trendhive.backend.dto;

import com.trendhive.backend.domain.Trend;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TrendResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String createdBy;
    private LocalDateTime createdAt;
    private String sourceUrl;
    private int likeCount;
    private int commentCount;

    public TrendResponseDTO(Trend trend) {
        this.id = trend.getId();
        this.title = trend.getTitle();
        this.description = trend.getDescription();
        this.category = trend.getCategory();
        this.createdBy = trend.getCreatedBy().getUsername(); // ✅ createdBy 추가
        this.createdAt = trend.getCreatedAt();
        this.sourceUrl = trend.getSourceUrl();
        this.likeCount = trend.getLikeCount();
        this.commentCount = trend.getCommentCount();
    }
}
