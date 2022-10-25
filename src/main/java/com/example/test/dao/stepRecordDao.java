package com.example.test.dao;

import com.example.test.bean.stepRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface stepRecordDao {
    int insert(stepRecord record);
}
