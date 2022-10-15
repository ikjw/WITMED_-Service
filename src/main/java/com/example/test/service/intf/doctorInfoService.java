package com.example.test.service.intf;
import com.example.test.bean.doctorInfo;



public interface doctorInfoService {
    /**
     * 用户信息插入
     * pre-condition:
     * UID不为空
     * post-condition:
     * 只插入满足，UID，name,gender字段不为空，且不与数据库内数据重复的数据；
     * @param doctorInfo 用户信息记录
     * @param mUID 用户唯一标识
     * @return 0成功 1失败
     */
    int insert(doctorInfo doctorInfo,String mUID);
    /**
     * pre-condition:
     * at不为空
     * post-condition：
     *
     * @param at UID/name
     * @return doctorInfo对象
     */
    doctorInfo query(String at);

    int update(doctorInfo doctorInfo);
}
