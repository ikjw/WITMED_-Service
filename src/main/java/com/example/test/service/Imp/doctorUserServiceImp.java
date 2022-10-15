package com.example.test.service.Imp;

import com.example.test.bean.doctorUser;
import com.example.test.dao.doctorUserDao;
import com.example.test.service.intf.doctorUserService;
import com.example.test.utils.CheckPreCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Slf4j
@Service
public class doctorUserServiceImp implements doctorUserService {
    @Resource
    doctorUserDao doctorUserDao;
    @Override
    @Transactional
    public int bind(String dUID,String uUID){
        CheckPreCondition.notNull(dUID);
        CheckPreCondition.notNull(uUID);
        int success= 0;
        if(doctorUserDao.query(uUID)!=null)
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
}
