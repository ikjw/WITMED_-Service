package com.example.test.service.Imp;

import com.example.test.bean.account;
import com.example.test.dao.accountDao;
import com.example.test.service.intf.accountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("accountService")
public class accountServiceImp implements accountService {
    @Resource
    accountDao  accountDao;
    @Override
    public account login(String at,int type) {
        //todo
        return accountDao.query(at,type);
    }
}
