package com.trendhive.backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrendRequestDTO {
    private String title;
    private String description;
    private String category;
    private String createdBy;
}
