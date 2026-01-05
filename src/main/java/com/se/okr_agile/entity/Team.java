package com.se.okr_agile.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Team {
    @TableId
    private Long id;
    private String name;
    private String description;
    private Integer cycleDays;
    private Integer status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Integer memberCount;
    private Integer okrCount;
    private Integer taskCount;
    private Integer completedTaskCount;
}
