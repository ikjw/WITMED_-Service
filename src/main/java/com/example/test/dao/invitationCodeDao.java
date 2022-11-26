package com.example.test.dao;

import com.example.test.bean.invitationCode;

import java.util.List;

public interface invitationCodeDao {
    int insert(String code,String dUID,String createTime);
    int update(String code,String uUID);
    List<invitationCode> query(int ignoreNum,int pageCount);
    List<invitationCode> get();
    int delete(String dUID,String code);
}
