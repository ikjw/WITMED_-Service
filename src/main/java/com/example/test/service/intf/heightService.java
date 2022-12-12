package com.example.test.service.intf;

import com.example.test.bean.height;

import java.time.LocalDate;

public interface heightService {
    int init(String UID, double height, LocalDate time);

    height queryRecent(String UID);
}
