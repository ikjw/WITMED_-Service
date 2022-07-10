package com.example.test.dao;

import com.example.test.entity.Food2;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IFood2Dao {
    Boolean add(@Param("user") String user, @Param("id") String id, @Param("cal") int cal, @Param("cdate") String cdate, @Param("time_slot") int time_slot);

    List<Food2> day(@Param("user") String user, @Param("cdate") String cdate);

}
