package com.example.test.service.intf;

import com.example.test.bean.account;

public interface accountService {
    /**
     * pre-condition:
     * at,pwd不为空
     * post-condition：
     *
     * @param at   UID/手机号/邮箱地址
     * @param type 用户类型
     * @return account对象
     */
    account login(String at, int type);

    /**
     * pre-condition:
     * UID 不为空
     * post-condition：
     *
     * @param UID UID
     * @return account对象
     */
    account queryByUIDWithoutPW(String UID);

    /**
     * pre-condition:
     * account对象不为空且UID不为空
     * post-condition：
     *
     * @param account account对象
     * @return 1/0
     */
    int update(account account);

}
