package com.example.test.service.Imp;

import com.example.test.bean.bloodSugar;
import com.example.test.dao.bloodSugarDao;
import com.example.test.service.intf.bloodSugarService;
import com.example.test.utils.CheckPreCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Slf4j
@Service
public class bloodSugarServiceImp implements bloodSugarService {
    @Resource
    bloodSugarDao  sugarDao;
    @Override
    public int batchInsert(List<bloodSugar> sugarList, String mUID) {
        CheckPreCondition.notNull(mUID);
        int count = 0;
        for(bloodSugar sugar : sugarList){
            count+=insert(sugar,mUID);
        }
        return count;
    }

    @Override
    public int insert(bloodSugar sugar, String mUID) {
        CheckPreCondition.notNull(mUID);
        if(sugar.getValue()<=0.00001) return 0;
        if(sugar.getTime()==null) return 0;
        sugar.setUID(mUID);
        int result = 0;
        try{
            result = sugarDao.insert(sugar);
        }catch (DuplicateKeyException e){
            log.debug(e.getMessage());
        }
        return result;
    }

    @Override
    public List<bloodSugar> query(String mUID, LocalDateTime from, LocalDateTime to) {
        CheckPreCondition.notNull(mUID);
        CheckPreCondition.notNull(to);
        CheckPreCondition.notNull(from);
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return sugarDao.query(mUID,from.format(fm),to.format(fm));
    }
    @Override
    public bloodSugar queryRecent(String mUID)
    {
        CheckPreCondition.notNull(mUID);
        return sugarDao.queryRecent(mUID);
    }
}
