package com.example.test.service.intf;

import com.example.test.bean.userInfo;

import java.util.List;

public interface userInfoService {
    /**
     * 用户信息插入
     * pre-condition:
     * UID不为空
     * post-condition:
     * 只插入满足，UID，name,gender字段不为空，且不与数据库内数据重复的数据；
     *
     * @param userInfo 用户信息记录
     * @param mUID     用户唯一标识
     * @return 0成功 1失败
     */
    int insert(userInfo userInfo, String mUID);

    /**
     * 高速批量插入用户信息
     * pre-condition:
     * UID不为空
     * post-condition:
     * 只插入满足，UID，name,gender字段不为空，且不与数据库内数据重复的数据；
     *
     * @param userlist 用户信息记录列表
     * @return 成功条数
     */
    int batchInsert(List<? extends userInfo> userlist);

    /**
     * pre-condition:
     * at不为空
     * post-condition：
     *
     * @param at UID/name
     * @return useInfo对象
     */
    userInfo query(String at);

    /**
     * pre-condition:
     * post-condition：
     *
     * @return useInfo对象列表
     */
    List<? extends userInfo> queryAll();

    int update(userInfo userInfo);
}
