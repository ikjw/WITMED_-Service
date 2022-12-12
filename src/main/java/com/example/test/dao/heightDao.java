package com.example.test.dao;

import com.example.test.bean.height;

public interface heightDao {
    int init(String UID,double height,String time);
    height queryRecent(String UID);
}
