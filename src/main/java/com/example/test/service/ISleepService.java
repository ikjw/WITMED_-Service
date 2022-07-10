package com.example.test.service;

import com.example.test.entity.Sleep;

import java.util.Date;
import java.util.List;

public interface ISleepService {
    Boolean add(int id, int len, String cdate, String user);

    List<Sleep> day(String user, String cdate);

}
