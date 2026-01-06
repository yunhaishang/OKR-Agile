package com.se.okr_agile.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class KeyResult {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long objective_id;
    private String description;
    private String metric_type;
    private BigDecimal target_value;
    private BigDecimal current_value;
    private String display_unit;
    private BigDecimal weight;
    private BigDecimal progress;
    private Long create_user_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}