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
    private LocalDateTime created_at;
}
