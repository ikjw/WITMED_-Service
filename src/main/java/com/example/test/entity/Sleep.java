package com.example.test.entity;

import java.util.Date;

public class Sleep {
    private int id;
    private int len;
    private String cdate;
    private String user;

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getLen() { return len; }
    public void setLen(int len) { this.len = len; }
    public String getCdate() { return cdate; }
    public void setCdate(String cdate) { this.cdate = cdate; }
}
