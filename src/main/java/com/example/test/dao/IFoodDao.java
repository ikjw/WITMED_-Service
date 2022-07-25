package com.example.test.dao;

import com.example.test.entity.Food;
import org.apache.ibatis.annotations.Param;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableAutoConfiguration
@Repository
public interface IFoodDao {
    List<Food> get(@Param("keyword") String keyword,@Param("ignoreNum")int ignoreNum,@Param("numOfpage") int numOfpage);
    Food getById(@Param("id") int id);
}
