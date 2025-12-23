package com.se.okr_agile.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateSprintRequestVO {
    private String name;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
}
