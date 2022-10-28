package com.example.test.service.intf;

import com.example.test.bean.account;

public interface registerService {
    String sendMs(String phone);

    int addAccount(account account);

}
