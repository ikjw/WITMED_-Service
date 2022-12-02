package com.example.test.service.Imp;

import com.example.test.bean.pregnancyInfo;
import com.example.test.dao.pregnancyInfoDao;
import com.example.test.service.intf.pregnancyInfoService;
import com.example.test.utils.CheckPreCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class pregnancyInfoServiceImp implements pregnancyInfoService {
    @Resource
    pregnancyInfoDao pregnancyInfoDao;
    @Override
    public int init(pregnancyInfo pregnancyInfo){
        CheckPreCondition.notNull(pregnancyInfo);
        if(pregnancyInfo.getPpHeight()<100)
            return 0;
        if (pregnancyInfo.getPpWeight()<0.001)
            return 0;
        int result = 0;
        try{
            result = pregnancyInfoDao.init(pregnancyInfo);
        }catch (DuplicateKeyException e){
            log.debug(e.getMessage());
        }
        return result;
    }
    @Override
    public int update(pregnancyInfo pregnancyInfo)
    {
        CheckPreCondition.notNull(pregnancyInfo);
        return pregnancyInfoDao.update(pregnancyInfo);
    }
    @Override
    public pregnancyInfo query(String UID){
        CheckPreCondition.notNull(UID);
        return pregnancyInfoDao.query(UID);
    }
}
