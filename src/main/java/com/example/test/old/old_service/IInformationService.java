package com.example.test.old.old_service;

import com.example.test.old.entity.Information;

import java.util.List;

public interface IInformationService {
    Information get(String username);

    int init(Information information);

    int update(Information information);

    int updateDisease(String username,String disease);

    List<Information> getByDoctor(String doctorUsername);
}
