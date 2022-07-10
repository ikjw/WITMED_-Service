package com.example.test.service.impl;

import com.example.test.dao.ISportDao;
import com.example.test.entity.Sport;
import com.example.test.service.ISportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SportService implements ISportService {
    @Autowired
    private ISportDao iSportDao;

    @Override
    public Boolean add(String user, String id, int cal, String cdate, int time_slot) {
        return iSportDao.add(user, id, cal, cdate, time_slot);
    }

    @Override
    public List<Sport> day(String user, String cdate) {
        return iSportDao.day(user,  cdate);
    }
}
