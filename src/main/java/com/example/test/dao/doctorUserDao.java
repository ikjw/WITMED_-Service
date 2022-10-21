package com.example.test.dao;

import com.example.test.bean.doctorUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface doctorUserDao {
    /**
     *添加医患关系
     * pre-condition:
     *都不为空
     * post-condition:
     * @return 0成功 1失败
     */
    int insert(doctorUser doctorUser);
    /**
     * pre-condition:
     * at不为空
     * post-condition：
     *
     * @param at uUID
     * @return doctorInfo对象
     */
    List<doctorUser> query(String at);
    /**
     * pre-condition:
     * at不为空
     * post-condition：
     * 修改绑定情况
     * @param at uUID
     * @return 1,0
     */
    int update(String at);
}
