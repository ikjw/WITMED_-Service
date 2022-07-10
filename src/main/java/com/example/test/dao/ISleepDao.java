package com.example.test.dao;

import com.example.test.entity.Sleep;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ISleepDao {
    Boolean add(@Param("id") int id, @Param("len") int len, @Param("cdate") String cdate, @Param("user") String user);

    List<Sleep> day(@Param("user") String user, @Param("cdate") String cdate);
}
