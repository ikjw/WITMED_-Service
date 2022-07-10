package com.example.test.service;

import com.example.test.entity.Food2;

import java.util.Date;
import java.util.List;

public interface IFood2Service {
    Boolean add(String user, String id, int cal, String cdate, int time_slot);

    List<Food2> day(String user, String cdate);
}
