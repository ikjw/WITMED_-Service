package com.example.test.service.Imp;
import com.example.test.bean.heartRate;
import com.example.test.dao.heartRateDao;
import com.example.test.service.intf.heartRateService;
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
public class heartRateServiceImp implements heartRateService{
    @Resource
    heartRateDao heartRateDao;
    @Override
    public int batchInsert(List<heartRate> rateList , String mUID){
        CheckPreCondition.notNull(mUID);
        int count = 0;
        for (heartRate heartRate : rateList) {
            count+=insert(heartRate,mUID);
        }
        return count;
    }
    @Override
    public int insert(heartRate rate,String mUID)
    {
        int result = 0;
        CheckPreCondition.notNull(mUID);
        if(rate.getValue()<1)
            return 0;
        if(rate.getTime()==null)
            return 0;
        rate.setUID(mUID);
        try{
            result = heartRateDao.insert(rate);
        }catch (DuplicateKeyException e){
            log.debug(e.getMessage());
        }
        return result;
    }
    public List<heartRate> query(String mUID,LocalDateTime from, LocalDateTime to)
    {
        CheckPreCondition.notNull(mUID);
        CheckPreCondition.notNull(from);
        CheckPreCondition.notNull(to);
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return heartRateDao.query(mUID,from.format(fm),to.format(fm));
    }
}
