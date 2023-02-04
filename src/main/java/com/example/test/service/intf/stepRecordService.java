package com.example.test.service.intf;

import com.example.test.bean.stepRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface stepRecordService {
    int insert(String UID, stepRecord record);
    int batchInsert(String UID, List<stepRecord> record);
    List<stepRecord> query(String UID, LocalDateTime from,LocalDateTime to);
    List<stepRecord> queryWithSource(String UID, LocalDateTime from,LocalDateTime to,int source);
}
