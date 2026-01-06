package com.se.okr_agile.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateKeyResultRequestVO {
    private Long objective_id;
    private String description;
    private String metric_type;
    private BigDecimal target_value;
    private String display_unit;
    private BigDecimal weight;
    private BigDecimal current_value;
}