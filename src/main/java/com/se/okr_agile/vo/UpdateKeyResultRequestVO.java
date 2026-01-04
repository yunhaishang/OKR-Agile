package com.se.okr_agile.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateKeyResultRequestVO {
    private String description;
    private String metric_type;
    private BigDecimal target_value;
    private String display_unit;
    private BigDecimal weight;
}