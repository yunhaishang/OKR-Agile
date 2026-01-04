package com.se.okr_agile.vo;

import com.se.okr_agile.entity.Team;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LoginVO {
    private String token;
    private UserInfo userInfo;
    
    @Data
    public static class UserInfo {
        private Long id;
        private String username;
        private String email;
        private String role;
        private LocalDateTime create_time;
        private List<Team> teams;
    }
}
