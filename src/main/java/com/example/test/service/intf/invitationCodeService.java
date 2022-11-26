package com.example.test.service.intf;

import com.example.test.bean.invitationCode;

import java.util.List;

public interface invitationCodeService {
    String createCode(String dUID);
    int findCode(String uUID,String code);
    List<invitationCode> query(int pageIndex,int pageCount);
    int deleteCode();
}
