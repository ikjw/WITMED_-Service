package com.example.test.old.old_service.impl;

import com.example.test.old.old_dao.IRegisterDao;
import com.example.test.old.old_service.IRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService implements IRegisterService {
    //@Autowired
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
