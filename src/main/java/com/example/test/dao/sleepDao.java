package com.example.test.dao;

import com.example.test.bean.sleep;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface sleepDao {
    /**
     * precondition:
     * sleep的UID,start,end,type字段不为空
     * 插入一条睡眠记录
     * @param sleep 血糖记录对应的POJO
     * @return 成功1 失败0
     */
    int insert(sleep sleep);
    /**
     * pre-condition:
     * UID,from,to都不为空
     * post-condition:
     *  UID==UID && time>= from && time<to
     * @param UID 用户唯一标识UID
     * @param from 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @param to 结束时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 满足post-condition的睡眠记录
     */
    List<sleep> query(String UID, String from, String to);
}
