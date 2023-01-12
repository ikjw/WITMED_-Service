package com.example.test.dao;

import com.example.test.bean.cases;

import java.util.List;

public interface casesDao {
    int insert(String UID,String time,String img);
    List<cases>  query(String UID);
}
