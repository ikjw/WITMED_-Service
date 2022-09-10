package com.example.test.dao;

import com.example.test.bean.bloodSugar;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface bloodSugarDao {
    /**
     * precondition:
     * sugar的UID，time,value,type字段不为空
     * 插入一条血糖记录
     * @param sugar 血糖记录对应的POJO
     * @return 成功1 失败0
     */
    int insert(bloodSugar sugar);

    /**
     * pre-condition:
     * UID,from,to都不为空
     * post-condition:
     *  UID==UID && time>= from && time<to
     * @param UID 用户唯一标识UID
     * @param from 开始时间 格式 yyyy-MM-dd HH:mm:ss
     * @param to 结束时间 格式 yyyy-MM-dd HH:mm:ss
     * @return 满足post-condition的血糖记录
     */
    List<bloodSugar> query(String UID,String from,String to);

    /**
     * pre-condition:
     * UID不为空
     * post-condition：
     *  时间最近的一次血糖记录
     * @param UID 用户唯一标识UID
     * @return 最近的一次血糖记录
     */
    bloodSugar queryRecent(String UID);
}
