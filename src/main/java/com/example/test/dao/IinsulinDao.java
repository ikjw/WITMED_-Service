package com.example.test.dao;

import com.example.test.entity.insulin;
import org.apache.ibatis.annotations.Param;

public interface IinsulinDao {
     int insert(@Param("username") String username,@Param("volume") double volume,@Param("datetime") String datetime);
     insulin getRecent(@Param("username") String username);
}
