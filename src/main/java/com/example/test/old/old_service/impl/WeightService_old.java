package com.example.test.old.old_service.impl;

import com.example.test.old.old_dao.IWeightDao;
import com.example.test.old.old_service.IWeightService;
import com.example.test.old.entity.Weight;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class WeightService_old implements IWeightService {
    //@Autowired
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
