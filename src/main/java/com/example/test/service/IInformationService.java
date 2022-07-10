package com.example.test.service;

import com.example.test.entity.Information;

import java.util.List;

public interface IInformationService {
    Information get(String username);

    void addnew(Information information);
    void update(Information information);

    void updatedis(String username, String diseases);

    Information getdis(String username);

    List<Information> getAll();
}
