package com.example.test.entity;

import java.util.Objects;

public class Login {
    private int id;//注册顺序
    private String username;
    private String password;
    private int role;//0为管理员,1为普通用户,2为医生

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return role == login.role && username.equals(login.username) && password.equals(login.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, role);
    }
}

