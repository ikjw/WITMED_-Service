package com.example.test.old.old_service;

public interface IRegisterService {
    void register(String username, String password);
    int registerDoctor(String username, String password,int role);
}
