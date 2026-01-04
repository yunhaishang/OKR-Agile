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
    
    @TableField("create_time")
    private LocalDateTime create_time;
    
    private List<Team> teams;
}
