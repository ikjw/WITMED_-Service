package com.example.test.service.intf;

import com.example.test.bean.dietRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface dietRecordService {
    int insert(dietRecord record, String mUID);
    List<dietRecord> query(String UID, LocalDateTime from, LocalDateTime to);
}
