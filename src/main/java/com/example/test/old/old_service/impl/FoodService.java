package com.example.test.old.old_service.impl;

import com.example.test.old.old_dao.IFoodDao;
import com.example.test.old.entity.Food;
import com.example.test.old.old_service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService implements IFoodService {
    //@Autowired
    private IFoodDao iFoodDao;


    @Override
    public List<Food> get(String keyword, int pageIndex) {
        return iFoodDao.get(keyword,(pageIndex-1)*20,20);
    }

    @Override
    public Food getById(int id) {
        return  iFoodDao.getById(id);
    }
}
