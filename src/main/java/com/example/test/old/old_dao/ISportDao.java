package com.example.test.old.old_dao;

import com.example.test.old.entity.Sport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISportDao {
    Boolean add(@Param("user") String user, @Param("id") String id, @Param("cal") int cal, @Param("cdate") String cdate, @Param("time_slot") int time_slot);
    List<Sport> day(@Param("user") String user, @Param("cdate") String cdate);}
