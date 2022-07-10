package com.example.test.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IDoctorPatientDao {
    int bind(@Param("doctorUsername") String doctorUsername, @Param("patientUsername") String patientUsername);
}
