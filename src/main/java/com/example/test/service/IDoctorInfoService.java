package com.example.test.service;

import com.example.test.entity.DoctorInfo;

import java.util.List;


public interface IDoctorInfoService {
    DoctorInfo query(String userName);
    int insert(DoctorInfo info);
    int update(DoctorInfo info);
    int delete(String userName);
    List<DoctorInfo> getList();
    int bind(String doctorUsername, String patientUsername);
}
