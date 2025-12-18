package com.se.okr_agile.entity;

import lombok.Data;

@Data
public class User {
    private long id;
    private String username;
    private String email;
    private String role;
    private String password;
}
