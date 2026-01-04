package com.se.okr_agile.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTaskKrRequestVO {
    private Long task_id;
    private Long kr_id;
    private BigDecimal weight;
}