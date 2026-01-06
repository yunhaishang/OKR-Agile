package com.se.okr_agile.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateRetrospectiveReportRequestVO {
    private Long team_id;
    private Long sprint_id;
    private String title;
    private String highlights;
    private String bottlenecks;
    private String suggestions;
    private String metrics_json;
    private Integer status;
    private Long generated_by_user_id;
    private LocalDateTime generated_at;
}