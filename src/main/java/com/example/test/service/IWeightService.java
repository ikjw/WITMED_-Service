package com.example.test.service;

import com.example.test.entity.Bg;
import com.example.test.entity.Weight;

import java.sql.Date;
import java.util.List;

public interface IWeightService {
    int add(String username, double weight, Date date);
    List<Weight> getWeightList(String username,Date start,Date end);
    Weight getRecentWeight(String username);
}
