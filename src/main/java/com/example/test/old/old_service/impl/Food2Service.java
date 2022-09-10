package com.example.test.old.old_service.impl;

import com.example.test.old.old_dao.IFood2Dao;
import com.example.test.old.old_service.IFood2Service;
import com.example.test.old.entity.Food2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Food2Service implements IFood2Service {
    //@Autowired
    private IFood2Dao iFood2Dao;

    @Override
    public int add(String user, String id, int cal, String cdate, int time_slot) {
        return iFood2Dao.add(user, id, cal, cdate, time_slot);
    }

    @Override
    public List<Food2> day(String user, String cdate) {
        return iFood2Dao.day(user, cdate);
    }
}
