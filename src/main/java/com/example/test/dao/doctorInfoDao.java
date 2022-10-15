package com.example.test.dao;

import com.example.test.bean.doctorInfo;

import org.springframework.stereotype.Repository;

@Repository

public interface doctorInfoDao {
    /**
     * precondition:
     * doctorInfo的UID字段不为空
     * 插入一条用户记录
     * @param doctorInfo 用户信息对应的POJO
     * @return 成功1 失败0
     */
    int insert(doctorInfo doctorInfo);
    /**
     * 查询:
     * UID==at || name==at
     * 的userInfo记录
     * @param at UID/name
     * @return account对象
     */
    doctorInfo query(String at);
    /**
     * 更新:
     * 传入的的userInfo记录
     * @return 成功1 失败0
     */
    int update(doctorInfo doctorInfo);
}
