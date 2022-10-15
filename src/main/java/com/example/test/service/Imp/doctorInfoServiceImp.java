package com.example.test.service.Imp;

import com.example.test.bean.doctorInfo;
import com.example.test.dao.doctorInfoDao;
import com.example.test.service.intf.doctorInfoService;

import com.example.test.utils.CheckPreCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
@Transactional
public class doctorInfoServiceImp implements doctorInfoService {
    @Resource
    doctorInfoDao doctorInfoDao;
    @Override
    public int insert(doctorInfo doctorInfo,String mUID){
        int gender = doctorInfo.getGender();
        String name = doctorInfo.getName();
        CheckPreCondition.notNull(mUID);
        if(gender!=0&&gender!=1) return 0;
        if(name==null) return 0;
        doctorInfo.setUID(mUID);
        int result =0;
        try{
            result = doctorInfoDao.insert(doctorInfo);
        }catch (DuplicateKeyException e){
            log.debug(e.getMessage());
        }
        return result;
    }
    @Override
    public doctorInfo query(String at){
        CheckPreCondition.notNull(at);
        return doctorInfoDao.query(at);
    }
    @Override
    public int update(doctorInfo doctorInfo){
        CheckPreCondition.notNull(doctorInfo);
        return doctorInfoDao.update(doctorInfo);
    }
}
