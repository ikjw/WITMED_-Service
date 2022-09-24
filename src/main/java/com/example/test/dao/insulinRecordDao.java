package com.example.test.dao;

import com.example.test.bean.insulinRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface insulinRecordDao {
    int insert(insulinRecord record);
    List<insulinRecord> query(String mUID,String from,String to);
}
