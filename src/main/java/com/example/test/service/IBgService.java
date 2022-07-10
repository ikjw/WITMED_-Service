package com.example.test.service;

import com.example.test.entity.Bg;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface IBgService {
    Boolean add(int id, double bg_value, String time, int time_slot);
    List<Bg> day(int id, String time);
    List<Bg> week(int id, String time);
    List<Bg> month(int id,String time);
}
