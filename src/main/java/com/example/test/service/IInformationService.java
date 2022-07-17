package com.example.test.service;

import com.example.test.entity.Information;

import java.util.List;

public interface IInformationService {
    Information get(String username);

    int init(Information information);

    int update(Information information);

    int updateDisease(String username,String disease);

    List<Information> getByDoctor(String doctorUsername);
}
