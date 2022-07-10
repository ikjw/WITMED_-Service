package com.example.test.service.impl;

import com.example.test.dao.ISleepDao;
import com.example.test.entity.Sleep;
import com.example.test.service.ISleepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SleepService implements ISleepService {
    @Autowired
    private ISleepDao iSleepDao;

    @Override
    public Boolean add(int id, int len, String cdate, String user) {
        return iSleepDao.add(id, len, cdate, user);
    }


    @Override
    public List<Sleep> day(String user, String cdate) {
        return iSleepDao.day(user, cdate);
    }
}