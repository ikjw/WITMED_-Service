package com.example.test.old.old_service;


import com.example.test.old.entity.dietplan;

import java.util.List;

public interface IdietplanService {
    int add(dietplan plan);
    List<dietplan> queryByPatient(String targetUsername);
    List<dietplan> queryByDoctor(String doctorUsername);
    List<dietplan> queryByDoctorAndPatient(String targetUsername,String doctorUsername);
}
