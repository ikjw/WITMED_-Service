package com.example.test.old.entity;

import lombok.Data;

import java.util.Objects;

@Data
public class Login {
    private Integer id;//注册顺序
    private String username;
    private String password;
    private Integer role;//0为管理员,1为普通用户,2为医生
}

