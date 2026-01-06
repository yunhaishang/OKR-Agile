package com.se.okr_agile.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Objective {
    @TableId
    private Long id;
    private Long team_id;
    private Long sprint_id;
    private String title;
    private String description;
    private BigDecimal progress;
    private LocalDateTime cycleStart;
    private LocalDateTime cycleEnd;
    private Integer status;
    private Long createUserId;
    private LocalDateTime created_at;
}