package com.se.okr_agile.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("task_kr")
public class TaskKr {
    private Long task_id;
    private Long kr_id;
    private BigDecimal weight;
}