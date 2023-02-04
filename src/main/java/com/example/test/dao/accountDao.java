package com.example.test.dao;

import com.example.test.bean.account;
import org.springframework.stereotype.Repository;

@Repository
public interface accountDao {
    /**
     * 查询:
     *      (UID==at || 手机号==at || 邮箱地址==at) && 用户类型==type
     *      的account记录
     * @param at UID/手机号/邮箱地址
     * @param type 角色类型
     * @return account对象
     */
    account query( String at, int type);

    /**
     * 按UID查询，不返回密码
     * @param UID UID
     * @return account对象
     */
    account queryByUIDWithoutPW(String UID);

    /**
     * 插入
     *
     * @return 1/0
     */
    int insert(account account);

    /**
     * 更新
     *
     * @return 1/0
     */
    int update(account account);
    int updatePsw(String oldPwd,String newPwd,String UID);
}
