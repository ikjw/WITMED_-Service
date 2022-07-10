package com.example.test.dao;

import com.example.test.entity.Disease;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDiseaseDao {
    List<Disease> get(@Param("name")String name);
}
