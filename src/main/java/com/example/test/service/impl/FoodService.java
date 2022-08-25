package com.example.test.service.impl;

import com.example.test.dao.IFoodDao;
import com.example.test.entity.Food;
import com.example.test.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService implements IFoodService {
    @Autowired
    private IFoodDao iFoodDao;

    @Override
    public List<Food> login1(String name) {return iFoodDao.login1(name);}
    public List<Food> login2(String name) {return iFoodDao.login2(name);}
    public List<Food> login3(String name) {return iFoodDao.login3(name);}
    public List<Food> login4(String name) {return iFoodDao.login4(name);}
    public List<Food> login5(String name) {return iFoodDao.login5(name);}
    public List<Food> login6(String name) {return iFoodDao.login6(name);}
    public List<Food> login7(String name) {return iFoodDao.login7(name);}
    public List<Food> idsearch(int id) {return iFoodDao.idsearch(id);}

    @Override
    public List<Food> get(String keyword, int pageIndex) {
        return iFoodDao.get(keyword,(pageIndex-1)*20,20);
    }

    @Override
    public Food getById(int id) {
        return  iFoodDao.getById(id);
    }
}
