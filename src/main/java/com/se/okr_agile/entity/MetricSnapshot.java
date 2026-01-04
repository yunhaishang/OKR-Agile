package com.se.okr_agile.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MetricSnapshot {
    private Long id;
    private Long team_id;
    private Long sprint_id;
    private LocalDate snapshot_date;
    private BigDecimal okr_progress;
    private Integer total_tasks;
    private Integer completed_tasks;
    private Integer blocked_tasks;
    private BigDecimal velocity;
    private java.time.LocalDateTime created_at;
}