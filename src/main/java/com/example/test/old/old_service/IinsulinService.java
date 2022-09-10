package com.example.test.old.old_service;

import com.example.test.old.entity.insulin;

public interface IinsulinService {
    public int insert(insulin insulin);
    public insulin getRecent(String username);
}
