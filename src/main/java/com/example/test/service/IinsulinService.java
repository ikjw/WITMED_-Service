package com.example.test.service;

import com.example.test.entity.insulin;

public interface IinsulinService {
    public int insert(insulin insulin);
    public insulin getRecent(String username);
}
