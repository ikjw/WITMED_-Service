package com.example.test.service.impl;

import com.example.test.dao.IFood2Dao;
import com.example.test.entity.Food2;
import com.example.test.service.IFood2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class Food2Service implements IFood2Service {
    @Autowired
    private IFood2Dao iFood2Dao;

    @Override
    public Boolean add(String user, String id, int cal, String cdate, int time_slot) {
        return iFood2Dao.add(user, id, cal, cdate, time_slot);
    }

    @Override
    public List<Food2> day(String user, String cdate) {
        return iFood2Dao.day(user, cdate);
    }
}
