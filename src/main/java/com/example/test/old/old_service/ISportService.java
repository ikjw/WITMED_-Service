package com.example.test.old.old_service;

import com.example.test.old.entity.Sport;

import java.util.List;

public interface ISportService {
    Boolean add(String user, String id, int cal, String cdate, int time_slot);

    List<Sport> day(String user, String cdate);
}
