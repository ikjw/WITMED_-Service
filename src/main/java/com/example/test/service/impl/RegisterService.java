package com.example.test.service.impl;

import com.example.test.dao.IRegisterDao;
import com.example.test.service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService implements IRegisterService {
    @Autowired
    private IRegisterDao iRegisterDao;

    @Override
    public void register(String username, String password) {
        iRegisterDao.register(username, password);
    }

    @Override
    public int registerDoctor(String username, String password, int role) {
        return iRegisterDao.registerDoctor(username,password,role);
    }
}
