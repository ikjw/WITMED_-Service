package com.example.test.dao;

import com.example.test.bean.account;
import org.apache.ibatis.annotations.Param;
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
}
