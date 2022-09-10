package com.example.test.old.old_service;

import com.example.test.old.entity.Login;

public interface ILoginService {
    /**
     * 登录
     * @param login
     * @return 1 成功   0失败
     */
    int login(Login login);
    /**
     * 注册
     * @param login
     * @return 1成功 0失败或用户名重复
     */
    int register(Login login);
}