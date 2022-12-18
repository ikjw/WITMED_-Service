package com.example.test.dao;

import com.example.test.bean.version;

public interface versionDao {
    version queryRecent(String name);
    version queryVersion(int versionCode,String name);
    int insert(version version);
}
