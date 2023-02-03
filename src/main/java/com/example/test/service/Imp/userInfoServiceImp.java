package com.example.test.service.Imp;

import com.example.test.bean.userInfo;
import com.example.test.dao.userInfoDao;
import com.example.test.service.intf.userInfoService;
import com.example.test.utils.CheckPreCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class userInfoServiceImp implements userInfoService{
    @Resource
    userInfoDao userInfoDao;
    @Override
    public int insert(userInfo userInfo,String mUID) {
        int gender = userInfo.getGender();
        String name = userInfo.getName();
        CheckPreCondition.notNull(mUID);
        if (gender != 0 && gender != 1) return 0;
        if (name == null) return 0;
        userInfo.setUID(mUID);
        int result = 0;
        try {
            result = userInfoDao.insert(userInfo);
        } catch (DuplicateKeyException e) {
            log.debug(e.getMessage());
        }
        return result;
    }

    @Override
    public int batchInsert(List<? extends userInfo> userlist) {
        if (userlist.isEmpty())
            return 0;
        List<userInfo> newUserlist = new ArrayList<>();
        for (userInfo d : userlist) {
            if (d.getUID() == null || d.getName() == null)
                continue;
            if (d.getGender() != 0 && d.getGender() != 1)
                d.setGender(0);
            newUserlist.add(d);
        }
        int result = 0;
        try {
            result = userInfoDao.batchInsert(newUserlist);
        } catch (DuplicateKeyException e) {
            log.debug(e.getMessage());
        }
        return result;
    }

    @Override
    public userInfo query(String at) {
        CheckPreCondition.notNull(at);
        return userInfoDao.query(at);
    }

    @Override
    public List<? extends userInfo> queryAll() {
        return userInfoDao.queryAll();
    }

    @Override
    public int update(userInfo userInfo) {
        CheckPreCondition.notNull(userInfo);
        return userInfoDao.update(userInfo);
    }
}
