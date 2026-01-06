package com.se.okr_agile.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateObjectiveRequestVO {
    private Long team_id;
    private Long sprint_id;
    private String title;
    private String description;
    private LocalDateTime cycleStart;
    private LocalDateTime cycleEnd;
    private Integer status;
    private Long createUserId;
}