package com.example.test.service.impl;

import com.example.test.dao.IBgDao;
import com.example.test.entity.Bg;
import com.example.test.service.IBgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Service
public class BgService implements IBgService {
    @Autowired
    private IBgDao iBgDao;

    @Override
    public Boolean add(String username, double bg_value, String time, int time_slot) {
        return iBgDao.add(username, bg_value, time, time_slot);
    }

    @Override
    public List<Bg> day(String username, String time) {
        return iBgDao.day(username, time);
    }

    ;

    @Override
    public List<Bg> week(String username, String time) {
        return iBgDao.week(username, time);
    }

    ;

    @Override
    public List<Bg> month(String username, String time) {
        return iBgDao.month(username, time);
    }
}
