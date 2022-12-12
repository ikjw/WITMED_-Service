package com.example.test.service.Imp;

import com.example.test.bean.doctorUser;
import com.example.test.bean.userInfo;
import com.example.test.dao.doctorUserDao;
import com.example.test.dao.userInfoDao;
import com.example.test.service.intf.doctorUserService;
import com.example.test.utils.CheckPreCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class doctorUserServiceImp implements doctorUserService {
    @Resource
    doctorUserDao doctorUserDao;
    @Resource
    userInfoDao userInfoDao;
    @Override
    @Transactional
    public int bind(String dUID,String uUID){
        CheckPreCondition.notNull(dUID);
        CheckPreCondition.notNull(uUID);
        int success= 0;
        if(doctorUserDao.query(uUID).size()!=0)
            return success;
        LocalDateTime time = LocalDateTime.now();
        int state = 1;
        doctorUser du = new doctorUser();
        du.setDUID(dUID);
        du.setUUID(uUID);
        du.setTime(time);
        du.setState(state);
        try {
            success= doctorUserDao.insert(du);
        }catch (DuplicateKeyException e){
            log.debug(e.getMessage());
        }
        return success;
    }
    @Override
    @Transactional
    public int unbind(String uUID){
        CheckPreCondition.notNull(uUID);
        if(doctorUserDao.query(uUID)==null)
            return 0;
        int success = doctorUserDao.update(uUID);
        return success;
    }
    @Override
    public List<doctorUser> getDoctor(String uUID){
        CheckPreCondition.notNull(uUID);
        return doctorUserDao.query(uUID);
    }
    public List<doctorUser> getPatient(String dUID){
        CheckPreCondition.notNull(dUID);
        return doctorUserDao.query(dUID);
    }
    @Override
    public int Find(String dUID,String uUID){
        CheckPreCondition.notNull(dUID);
        CheckPreCondition.notNull(uUID);
        if (doctorUserDao.find(dUID,uUID) == null) {
            return 0;
        }
        else return 1;
    }
    @Override
    public List<userInfo> getUnbind(){
        return userInfoDao.getUnbind();
    }
    @Override
    public  doctorUser isBind(String dUID,String uUID){
        CheckPreCondition.notNull(dUID);
        CheckPreCondition.notNull(uUID);
        return doctorUserDao.isBind(dUID,uUID);
    }
}
