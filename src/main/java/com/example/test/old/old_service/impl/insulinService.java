package com.example.test.old.old_service.impl;

import com.example.test.old.old_dao.IinsulinDao;
import com.example.test.old.entity.insulin;
import com.example.test.old.old_service.IinsulinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class insulinService implements IinsulinService {
    //@Autowired
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
