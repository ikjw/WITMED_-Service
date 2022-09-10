package com.example.test.old.old_dao;

import com.example.test.old.entity.datapointBean.sleepDataPoint;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ISleepDao {
    int insert(@Param("username") String username,
               @Param("type")int type,
               @Param("startTime") LocalDateTime startTime,
               @Param("endTime") LocalDateTime endTime);

    /**
     * 获取用户名为username的从 from 到 to 内的所有记录
     * 即
     *  username相同
     *  且 startTime >= from 或 startTime <= to的所有记录
     * @param username
     * @param from
     * @param to
     * @return
     */
    List<sleepDataPoint> query(@Param("username") String username,
                               @Param("from") LocalDateTime from,
                               @Param("to") LocalDateTime to);

}
