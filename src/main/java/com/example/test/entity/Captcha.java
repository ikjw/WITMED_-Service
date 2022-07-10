package com.example.test.entity;

import com.mysql.cj.jdbc.Blob;

public class Captcha {
    private Blob captcha;

    public Blob getCaptcha() {
        return captcha;
    }

    public void setCaptcha(Blob captcha) {
        this.captcha = captcha;
    }

    private int id;
    private String result;

    public int getId() {
        return id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setId(int id) {
        this.id = id;
    }

}
