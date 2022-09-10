package com.example.test.old.old_service;

import com.example.test.old.entity.Food2;

import java.util.List;

public interface IFood2Service {
    int add(String user, String id, int cal, String cdate, int time_slot);

    List<Food2> day(String user, String cdate);
}
