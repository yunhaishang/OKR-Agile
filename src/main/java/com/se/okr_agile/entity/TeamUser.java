package com.se.okr_agile.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TeamUser {
    private Long team_id;
    private Long user_id;
    private String role;
}
