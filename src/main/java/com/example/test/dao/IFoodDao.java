package com.example.test.dao;

import com.example.test.entity.Food;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableAutoConfiguration
@Repository
public interface IFoodDao {
    List<Food> login1(@Param("name") String name);
    List<Food> login2(@Param("name") String name);
    List<Food> login3(@Param("name") String name);
    List<Food> login4(@Param("name") String name);
    List<Food> login5(@Param("name") String name);
    List<Food> login6(@Param("name") String name);
    List<Food> login7(@Param("name") String name);
    List<Food> idsearch(@Param("id") int id);
}
