package com.example.test.dao;

import com.example.test.bean.version;

import java.util.List;

public interface versionDao {
    version queryRecent(String name);
    version queryVersion(int versionCode,String name);
    int insert(version version);
    List<version> query(int start, int end,String name);
    version queryVersionById(int id);
    int uploadFile(int id,String filename);
}
