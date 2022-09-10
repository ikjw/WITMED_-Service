package com.example.test.old.old_service.impl;

import com.example.test.old.old_dao.ILoginDao;
import com.example.test.old.old_service.ILoginService;
import com.example.test.old.entity.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LoginService implements ILoginService {
    //@Autowired
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
