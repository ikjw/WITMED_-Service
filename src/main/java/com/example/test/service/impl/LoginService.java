package com.example.test.service.impl;

import com.example.test.dao.ILoginDao;
import com.example.test.entity.Login;
import com.example.test.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {
    @Autowired
    private ILoginDao iLoginDao;

    @Override
    public Login login(String username, String password) {
        return iLoginDao.login(username, password);
    }

    @Override
    public Login loginuser(String username, String password, int role) {
        return iLoginDao.loginuser(username, password, role);
    }

    @Override
    public Login query(String username) {
        return iLoginDao.query(username);
    }

}
