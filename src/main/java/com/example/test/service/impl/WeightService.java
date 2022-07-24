package com.example.test.service.impl;

import com.example.test.dao.IBgDao;
import com.example.test.dao.IWeightDao;
import com.example.test.entity.Bg;
import com.example.test.entity.Weight;
import com.example.test.service.IBgService;
import com.example.test.service.IWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class WeightService implements IWeightService {
    @Autowired
    private IWeightDao iWeightDao;

    @Override
    public int add(String username, double weight, Date date) {
        return iWeightDao.add(username,weight,date);
    }

    @Override
    public List<Weight> getWeightList(String username, Date start, Date end) {
        return iWeightDao.getWeightList(username,start,end);
    }

    @Override
    public Weight getRecentWeight(String username) {
        return iWeightDao.getRecentWeight(username);
    }
}
