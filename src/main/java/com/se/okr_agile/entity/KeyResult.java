package com.se.okr_agile.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class KeyResult {
    private Long id;
    private Long objective_id;
    private String description;
    private String metric_type;
    private BigDecimal target_value;
    private BigDecimal current_value;
    private String display_unit;
    private BigDecimal weight;
    private BigDecimal progress;
    private LocalDateTime created_at;
}