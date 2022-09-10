package com.example.test.dao;

import com.example.test.bean.bloodSugar;
import com.example.test.bean.weight;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface weightDao {
    /**
     * precondition:
     * weight的UID，date,value字段不为空
     * 插入一条体重记录
     * @param wt 体重记录对应的POJO
     * @return 成功1 失败0
     */
    int insert(weight wt);

    /**
     * pre-condition:
     * UID,from,to都不为空
     * post-condition:
     * UID==UID&&time>=from&&time<to
     * @param UID 用户唯一标识UID
     * @param from 开始时间 格式 yyyy-MM-dd
     * @param to 结束时间 格式 yyyy-MM-dd
     * @return 满足post-condition的血糖记录
     */
    List<weight> query(String UID,String from,String to);

    /**
     * pre-condition:
     * UID不为空
     * post-condition：
     *  时间最近的一次体重记录
     * @param UID 用户唯一标识UID
     * @return 最近的一次体重记录
     */
    weight queryRecent(String UID);
}
