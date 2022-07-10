package com.example.test.service.impl;

import com.example.test.dao.IBgDao;
import com.example.test.dao.IWeightDao;
import com.example.test.entity.Bg;
import com.example.test.entity.Weight;
import com.example.test.service.IBgService;
import com.example.test.service.IWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeightService implements IWeightService {
    @Autowired
    private IWeightDao iWeightDao;

    @Override
    public Boolean add(int id, double weight, int week) {
        return iWeightDao.add(id, weight, week);
    }

    @Override
    public List<Weight> view(int id) {
        return iWeightDao.view(id);
    }
}
