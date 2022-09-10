package com.example.test.old.old_service;

import com.example.test.old.entity.DoctorInfo;

import java.util.List;


public interface IDoctorInfoService {
    DoctorInfo query(String userName);

    /**
     * 成功返回1 失败返回0
     * @param info
     * @return
     */
    int insert(DoctorInfo info);
    /**
     * 成功返回1 失败返回0
     * @param info
     * @return
     */
    int update(DoctorInfo info);
    /**
     * 成功返回1 失败返回0
     * @param userName
     * @return
     */
    int delete(String userName);
    List<DoctorInfo> getList();
    int bind(String doctorUsername, String patientUsername);
}
