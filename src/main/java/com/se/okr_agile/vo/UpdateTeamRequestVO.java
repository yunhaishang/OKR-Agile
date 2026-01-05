package com.se.okr_agile.vo;

import lombok.Data;

@Data
public class UpdateTeamRequestVO {
    private String name;
    private String description;
    private Integer cycleDays;
    private Integer status;
}