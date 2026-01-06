package com.se.okr_agile.vo;

import lombok.Data;

import java.time.LocalDateTime;

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
    private LocalDateTime due_date;
    private Integer estimated_hours;
    private Integer actual_hours;
    private Double code_contribution_score;
    private Long create_user_id;
}