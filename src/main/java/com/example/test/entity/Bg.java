package com.example.test.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;

public class Bg {
    private int id;

    private double bg_value;

    private String time;

    private int time_slot;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getBg_value() {
        return bg_value;
    }

    public void setBg_value(double bg_value) {
        this.bg_value = bg_value;
    }

    public int getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(int time_slot) {
        this.time_slot = time_slot;
    }
}
