package com.example.test.dao;

import com.example.test.bean.ketoneBody;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ketoneBodyDao {
    /**
     * pre-condition:
     * ketone-body表UID,value,time字段不为空
     * 插入一条酮体记录（阴性/阳性二选一）
     *
     * @param kb 新增的一条酮体记录
     * @return 成功1 失败0
     */
    int insert(ketoneBody kb);

    /**
     * pre-condition:
     * UID,from,to都不为空
     * post-condition:
     * UID==UID && time>= from && time<to
     *
     * @param UID  用户唯一标识UID
     * @param from 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @param to   结束时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 满足post-condition的酮体
     */
    List<ketoneBody> query(String UID, String from, String to);

    /**
     * pre-condition:
     * UID不为空
     * post-condition：
     * 时间最近的一次酮体记录
     *
     * @param UID 用户唯一标识UID
     * @return 最近的一次酮体记录
     */
    ketoneBody queryRecent(String UID);

}
