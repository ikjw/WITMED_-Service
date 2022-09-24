package com.example.test.service.intf;

import com.example.test.bean.insulinRecord;

import java.time.LocalDateTime;
import java.util.List;

public interface insulinRecordService {
    int insert(insulinRecord record,String mUID);

    int batchInsert(List<insulinRecord> recordList,String mUID);

    List<insulinRecord> query(String mUID, LocalDateTime from,LocalDateTime to);
}
