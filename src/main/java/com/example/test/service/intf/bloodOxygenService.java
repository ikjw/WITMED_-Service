package com.example.test.service.intf;

import com.example.test.bean.bloodOxygen;

import java.time.LocalDateTime;
import java.util.List;

public interface bloodOxygenService {
    /**
     * 血氧数据插入批处理
     * pre-condition:
     * mUID不为空
     * post-condition:
     * oxygenList中所有数据的UID字段都视为mUID
     * 只插入oxygenList中满足，value,time字段不为空，且不与数据库内数据重复的数据；
     * 重复：指UID与time字段相同
     * @param oxygenList 心率记录列表
     * @param mUID 用户唯一标识
     * @return 成功插入的数量
     */
    int batchInsert(List<bloodOxygen> oxygenList,String mUID);
    /**
     * 血氧数据插入
     * pre-condition:
     * mUID不为空
     * post-condition:
     * bloodOxygen中所有数据的UID字段都视为mUID
     * 只插入满足，value,time字段不为空，且不与数据库内数据重复的数据；
     * 重复：指UID与time字段相同
     * @param bloodOxygen 血糖记录
     * @param mUID 用户唯一标识
     * @return 0成功 1失败
     */
    int insert(bloodOxygen bloodOxygen,String mUID);
    /**
     * pre-condition:
     * mUID不为空
     * post-condition:
     * from和to都不为空
     * 若from和to都不为空时：
     * 查询time>=from 且 time < to的所有记录
     * 若from为空，to不为空:
     * 查询 time < to 的所有记录
     * 若from不为空，to为空:
     * 查询 time>=from 的所有记录
     * 若from和to都为空：
     * 查询 所有记录
     * @param mUID 用户唯一标识
     * @param from 查询起始时间
     * @param to 查询结束时间
     * @return 满足post-condition的所有血氧数据组成的列表
     */
    List<bloodOxygen> query(String mUID, LocalDateTime from, LocalDateTime to);
}
