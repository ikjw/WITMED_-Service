package com.example.test.old.old_dao;

import com.example.test.old.entity.Bg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBgDao {
    Boolean add(@Param("username") String username, @Param("bg_value") double bg_value, @Param("time") String time, @Param("time_slot") int time_slot);

    List<Bg> day(@Param("username") String username, @Param("time") String time);

    List<Bg> week(@Param("username") String username, @Param("time") String time);

    List<Bg> month(@Param("username") String username, @Param("time") String time);
}
