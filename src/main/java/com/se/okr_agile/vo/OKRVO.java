package com.se.okr_agile.vo;

import com.se.okr_agile.entity.KeyResult;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OKRVO {
    private Long id;
    private Long team_id;
    private Long sprint_id;
    private String title;
    private String description;
    private BigDecimal progress;
    private LocalDateTime created_at;
    private List<KeyResult> krs;
    private Integer taskCount;
    private Integer completedTaskCount;
}