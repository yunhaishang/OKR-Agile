package com.se.okr_agile.vo;

import lombok.Data;

@Data
public class CreateRetrospectiveReportRequestVO {
    private Long team_id;
    private Long sprint_id;
    private String title;
    private String highlights;
    private String bottlenecks;
    private String suggestions;
    private String metrics_json;
}