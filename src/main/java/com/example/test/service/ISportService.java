package com.example.test.service;

import com.example.test.entity.Sport;

import java.util.Date;
import java.util.List;

public interface ISportService {
    Boolean add(String user, String id, int cal, String cdate, int time_slot);

    List<Sport> day(String user, String cdate);
}
