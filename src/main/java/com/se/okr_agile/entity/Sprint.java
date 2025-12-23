package com.se.okr_agile.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Sprint {
    private Long id;
    private Long team_id;
    private String name;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String status;
}
