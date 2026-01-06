package com.se.okr_agile.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateObjectiveRequestVO {
    private String title;
    private String description;
    private LocalDateTime cycleStart;
    private LocalDateTime cycleEnd;
    private Integer status;
    private Long createUserId;
}