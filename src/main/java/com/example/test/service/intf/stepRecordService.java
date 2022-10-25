package com.example.test.service.intf;

import com.example.test.bean.stepRecord;

import java.util.List;

public interface stepRecordService {
    int insert(String UID, stepRecord record);
    int batchInsert(String UID, List<stepRecord> record);
}
