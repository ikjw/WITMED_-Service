package com.example.test.service.intf;

import com.example.test.bean.account;

public interface accountService {
    /**
     * pre-condition:
     * at,pwd不为空
     * post-condition：
     *
     * @param at UID/手机号/邮箱地址
     * @param type 用户类型
     * @return account对象
     */
    account login(String at,int type);
}
