package com.se.okr_agile.vo;

import lombok.Data;

@Data
public class UpdateRetrospectiveReportRequestVO {
    private String title;
    private String highlights;
    private String bottlenecks;
    private String suggestions;
    private String metrics_json;
}