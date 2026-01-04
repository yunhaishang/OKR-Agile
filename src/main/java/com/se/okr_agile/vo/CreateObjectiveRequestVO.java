package com.se.okr_agile.vo;

import lombok.Data;

@Data
public class CreateObjectiveRequestVO {
    private Long team_id;
    private Long sprint_id;
    private String title;
    private String description;
}