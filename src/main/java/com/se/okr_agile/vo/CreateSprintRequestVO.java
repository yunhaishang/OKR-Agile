package com.se.okr_agile.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateSprintRequestVO {
    private Long team_id;
    private String name;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
}
