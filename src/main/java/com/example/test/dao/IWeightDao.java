package com.example.test.dao;

import com.example.test.entity.Bg;
import com.example.test.entity.Weight;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IWeightDao {
    Boolean add(@Param("id") int id, @Param("weight") double weight, @Param("week") int week);

    List<Weight> view(@Param("id") int id);
}
