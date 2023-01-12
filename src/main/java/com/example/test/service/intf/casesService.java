package com.example.test.service.intf;

import com.example.test.bean.cases;

import java.time.LocalDateTime;
import java.util.List;

public interface casesService {
    int insert(String UID, String base64, LocalDateTime time);
    List<cases> query(String UID);
}
