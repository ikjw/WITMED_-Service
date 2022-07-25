package com.example.test.service;

import com.example.test.entity.Food;

import java.util.List;

public interface IFoodService {

    List<Food> get(String keyword,int pageIndex);
    Food getById(int id);
}
