package com.example.test.dao;

import com.example.test.entity.Bg;
import com.example.test.entity.Weight;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

public interface IWeightDao {
    int add(@Param("username") String username, @Param("weight") double weight, @Param("date") Date date);

    List<Weight> getWeightList(@Param("username") String username,@Param("start") Date start,@Param("end") Date end);

    Weight getRecentWeight(@Param("username") String username);
}
