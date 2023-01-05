package com.example.test.dao;

import com.example.test.bean.dietRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface dietRecordDao {
    Integer insert(dietRecord record);
    List<dietRecord> query(String UID,String from,String to);
    int update(String img,int id);
}
