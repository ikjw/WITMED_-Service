package com.example.test.service.intf;

import com.example.test.bean.pregnancyInfo;

public interface pregnancyInfoService {
    int init(pregnancyInfo pregnancyInfo);
    int update(pregnancyInfo pregnancyInfo);
    pregnancyInfo query(String UID);
}
