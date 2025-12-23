package com.se.okr_agile.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Team {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime created_at;
}
