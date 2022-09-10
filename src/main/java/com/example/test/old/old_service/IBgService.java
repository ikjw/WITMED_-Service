package com.example.test.old.old_service;

import com.example.test.old.entity.Bg;

import java.util.List;

public interface IBgService {
    Boolean add(String username, double bg_value, String time, int time_slot);
    List<Bg> day(String username, String time);
    List<Bg> week(String username, String time);
    List<Bg> month(String username,String time);
}
