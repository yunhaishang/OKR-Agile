package com.se.okr_agile.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.se.okr_agile.entity.Team;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserInfoVO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private LocalDateTime created_at;
    private List<Team> teams;
}
