package com.example.test.dao;

import com.example.test.bean.bloodOxygen;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface bloodOxygenDao {
    /**
     * precondition:
     * bloodOxygen的UID，time,value字段不为空
     * 插入一条血氧记录
     * @param bloodOxygen 血糖记录对应的POJO
     * @return 成功1 失败0
     */
    int insert(bloodOxygen bloodOxygen);
    List<bloodOxygen> query(String mUID,String from,String to);
}
