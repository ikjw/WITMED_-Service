package com.example.test.service.impl;

import com.example.test.dao.ILoginDao;
import com.example.test.entity.Login;
import com.example.test.service.ILoginService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LoginService implements ILoginService {
    @Autowired
    private ILoginDao iLoginDao;


    /**
     * 登录
     * @param login
     * @return 1 成功   0失败
     */
    @Override
    public int login(Login login) {
        Login in = iLoginDao.queryByAll(login.getUsername(),login.getPassword(),login.getRole());
        return in != null?1:0;
    }

    /**
     * 注册
     * @param login
     * @return 1成功 0失败或用户名重复
     */
    @Override
    public int register(Login login) {
        Login in = iLoginDao.queryByUsername(login.getUsername());
        if(in == null){
            return iLoginDao.insert(login.getUsername(),login.getPassword(),login.getRole());
        }
        return 0;
    }
}
