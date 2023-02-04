package com.example.test.dao;

import com.example.test.bean.stepRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface stepRecordDao {
    int insert(stepRecord record);
    List<stepRecord> query(String uUID,String from,String to);
    List<stepRecord> queryWithSource(String uUID,String from,String to,int source);
}
