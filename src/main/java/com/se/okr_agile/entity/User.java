package com.se.okr_agile.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String role;
    private String nickname;
    private String avatar;
    private Integer status;
    
    @TableField("create_time")
    private LocalDateTime create_time;
    
    @TableField("update_time")
    private LocalDateTime update_time;
}
