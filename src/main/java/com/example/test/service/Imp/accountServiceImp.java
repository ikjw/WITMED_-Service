package com.example.test.service.Imp;

import com.example.test.bean.account;
import com.example.test.dao.accountDao;
import com.example.test.service.intf.accountService;
import com.example.test.utils.CheckPreCondition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("accountService")
public class accountServiceImp implements accountService {
    @Resource
    accountDao  accountDao;
    @Override
    public account login(String at, int type) {
        //todo
        return accountDao.query(at, type);
    }

    @Override
    public account queryByUIDWithoutPW(String UID) {
        CheckPreCondition.notNull(UID);
        return accountDao.queryByUIDWithoutPW(UID);
    }

    @Override
    public int update(account account) {
        CheckPreCondition.notNull(account);
        CheckPreCondition.notNull(account.getUID());
        return accountDao.update(account);
    }
    @Override
    public int updatePsw(String UID,String oldPsw,String newPsw){
        CheckPreCondition.notNull(UID);
        CheckPreCondition.notNull(oldPsw);
        CheckPreCondition.notNull(newPsw);
        return accountDao.updatePsw(oldPsw,newPsw,UID);
    }
    @Override
    public int updateType(String UID){
        CheckPreCondition.notNull(UID);
        return accountDao.updateType(UID);
    }
}
