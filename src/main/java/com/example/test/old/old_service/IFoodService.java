package com.example.test.old.old_service;

import com.example.test.old.entity.Food;

import java.util.List;

public interface IFoodService {

    List<Food> get(String keyword,int pageIndex);
    Food getById(int id);
}
