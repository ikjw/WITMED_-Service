package com.example.test.service;

import com.example.test.entity.Bg;
import com.example.test.entity.Weight;

import java.util.List;

public interface IWeightService {
    Boolean add(int id, double weight, int week);
    List<Weight> view(int id);
}
