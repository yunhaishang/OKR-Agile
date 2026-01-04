package com.se.okr_agile.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateMetricSnapshotRequestVO {
    private Long team_id;
    private Long sprint_id;
    private LocalDate snapshot_date;
    private BigDecimal okr_progress;
    private Integer total_tasks;
    private Integer completed_tasks;
    private Integer blocked_tasks;
    private BigDecimal velocity;
}