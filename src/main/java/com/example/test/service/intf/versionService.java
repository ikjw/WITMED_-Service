package com.example.test.service.intf;

import com.example.test.bean.version;

public interface versionService {
    version queryRecent(String name);
    version queryVersion(int versionCode,String name);
    int insert(version version);
}
