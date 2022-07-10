package com.example.test.service;

public interface IRegisterService {
    void register(String username, String password);
    int registerDoctor(String username, String password,int role);
}
