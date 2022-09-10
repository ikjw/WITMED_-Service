package com.example.test.old.old_dao;

import com.example.test.old.entity.insulin;
import org.apache.ibatis.annotations.Param;

public interface IinsulinDao {
     int insert(@Param("username") String username,@Param("volume") double volume,@Param("datetime") String datetime);
     insulin getRecent(@Param("username") String username);
}
