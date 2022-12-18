package com.example.test.service.intf;

import com.example.test.bean.version;

import java.util.List;

public interface versionService {
    version queryRecent(String name);
    version queryVersion(int versionCode,String name);
    int insert(version version);
    List<version> query(int start, int end);
}
