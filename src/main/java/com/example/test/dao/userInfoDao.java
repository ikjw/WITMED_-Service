package com.example.test.dao;

import com.example.test.bean.userInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface userInfoDao {
    /**
     * precondition:
     * userInfo的UID，name,gender字段不为空
     * 插入一条用户记录
     *
     * @param userInfo 用户信息对应的POJO
     * @return 成功1 失败0
     */
    int insert(userInfo userInfo);

    /**
     * precondition:
     * 每个userInfo的UID字段不为空
     * Mybatis原生批量插入记录，最大5M大小
     *
     * @param userlist 医生信息列表
     * @return 成功条数
     */
    int batchInsert(List<userInfo> userlist);

    /**
     * 查询:
     * UID==at || name==at
     * 的userInfo记录
     *
     * @param at UID/name
     * @return account对象
     */
    userInfo query(String at);

    /**
     * 查询全部userInfo记录
     *
     * @return account对象的列表
     */
    List<? extends userInfo> queryAll();

    /**
     * 更新:
     * 传入的的userInfo记录
     *
     * @return 成功1 失败0
     */
    int update(userInfo userInfo);

    List<userInfo> getUnbind();

}
