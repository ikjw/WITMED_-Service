package com.example.test.service.impl;

import com.example.test.dao.IinsulinDao;
import com.example.test.entity.insulin;
import com.example.test.service.IinsulinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class insulinService implements IinsulinService {
    @Autowired
    private IinsulinDao dao;

    @Override
    public int insert(insulin insulin) {
        return dao.insert(insulin.getUsername(),insulin.getVolume(),insulin.getDatetime());
    }

    @Override
    public insulin getRecent(String username) {
        return dao.getRecent(username);
    }
}
