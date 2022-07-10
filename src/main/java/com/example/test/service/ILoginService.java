package com.example.test.service;

import com.example.test.entity.Login;

public interface ILoginService {
    Login login(String username, String password);
    Login loginuser(String username, String password, int role);

    Login query(String username);
}
