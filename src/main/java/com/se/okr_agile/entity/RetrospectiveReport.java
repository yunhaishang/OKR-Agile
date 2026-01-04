package com.se.okr_agile.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RetrospectiveReport {
    private Long id;
    private Long team_id;
    private Long sprint_id;
    private String title;
    private String highlights;
    private String bottlenecks;
    private String suggestions;
    private String metrics_json;
    private LocalDateTime created_at;
}