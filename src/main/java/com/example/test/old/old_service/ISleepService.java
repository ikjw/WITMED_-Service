package com.example.test.old.old_service;

import com.example.test.old.entity.datapointBean.sleepDataPoint;

import java.util.List;

public interface ISleepService {
    int batchInsert(List<sleepDataPoint> lst);

}
