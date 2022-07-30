package com.example.test.dao;

import com.example.test.entity.Bg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface IBgDao {
    Boolean add(@Param("username") String username, @Param("bg_value") double bg_value, @Param("time") String time, @Param("time_slot") int time_slot);

    List<Bg> day(@Param("username") String username, @Param("time") String time);

    List<Bg> week(@Param("username") String username, @Param("time") String time);

    List<Bg> month(@Param("username") String username, @Param("time") String time);
}
