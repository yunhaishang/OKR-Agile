package com.se.okr_agile.vo;

import lombok.Data;

@Data
public class CreateTaskRequestVO {
    private Long team_id;
    private Long sprint_id;
    private Long kr_id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String type;
    private Integer story_points;
    private Long assignee_id;
}