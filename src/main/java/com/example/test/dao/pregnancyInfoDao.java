package com.example.test.dao;

import com.example.test.bean.pregnancyInfo;

public interface pregnancyInfoDao {
    int init(pregnancyInfo pregnancyInfo);

    int update(pregnancyInfo pregnancyInfo);

    pregnancyInfo query(String UID);
}
