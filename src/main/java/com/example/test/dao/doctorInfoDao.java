package com.example.test.dao;

import com.example.test.bean.doctorInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface doctorInfoDao {
    /**
     * precondition:
     * doctorInfo的UID字段不为空
     * 插入一条用户记录
     *
     * @param doctorInfo 用户信息对应的POJO
     * @return 成功1 失败0
     */
    int insert(doctorInfo doctorInfo);

    /**
     * precondition:
     * 每个doctorInfo的UID字段不为空
     * Mybatis原生批量插入记录，最大5M大小
     *
     * @param doctorlist 医生信息列表
     * @return 成功条数
     */
    int batchInsert(List<doctorInfo> doctorlist);

    /**
     * 查询:
     * UID==at || name==at
     * 的doctorInfo记录
     *
     * @param at UID/name
     * @return account对象
     */
    doctorInfo query(String at);

    /**
     * 查询所有doctorInfo记录
     *
     * @return account对象
     */
    List<? extends doctorInfo> queryAll();

    /**
     * 更新:
     * 传入的的doctorInfo记录
     *
     * @return 成功1 失败0
     */
    int update(doctorInfo doctorInfo);
}
