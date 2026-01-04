package com.se.okr_agile.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Task {
    private Long id;
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
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}