package com.se.okr_agile.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Sprint {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long team_id;
    private String name;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private String status;
}
