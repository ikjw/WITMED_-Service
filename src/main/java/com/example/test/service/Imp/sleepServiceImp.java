package com.example.test.service.Imp;
import com.example.test.bean.sleep;
import com.example.test.dao.sleepDao;
import com.example.test.service.intf.sleepService;
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

public class sleepServiceImp implements sleepService{
    @Resource
    sleepDao sleepDao;
    @Override
    public int insert(sleep sleep,String mUID){
        int result =0;
        CheckPreCondition.notNull(mUID);
        if(sleep.getType()>5&&sleep.getType()<0)
            return result;
        if(sleep.getStart()==null)
            return result;
        if(sleep.getEnd()==null)
            return result;
        sleep.setUID(mUID);
        try {
            result = sleepDao.insert(sleep);
        }catch (DuplicateKeyException e)
        {
            log.debug(e.getMessage());
        }
        return result;
    }
    @Override
    public int batchInsert(List<sleep>sleepList,String mUID){
        CheckPreCondition.notNull(mUID);
        int result = 0;
        for (sleep sleep : sleepList) {
            result =insert(sleep,mUID);
        }
        return result;
    }
    public List<sleep> query(String mUID,LocalDateTime from,LocalDateTime to){
        CheckPreCondition.notNull(mUID);
        CheckPreCondition.notNull(from);
        CheckPreCondition.notNull(to);
        DateTimeFormatter fm =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return sleepDao.query(mUID, from.format(fm),to.format(fm));
    }
}
