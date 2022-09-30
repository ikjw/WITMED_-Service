package com.example.test.service.Imp;

import com.example.test.bean.bloodOxygen;
import com.example.test.dao.bloodOxygenDao;
import com.example.test.service.intf.bloodOxygenService;
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
public class bloodOxygenServiceImp implements bloodOxygenService{
    @Resource bloodOxygenDao bloodOxygenDao;
    public int insert(bloodOxygen bloodOxygen,String mUID){
        int result = 0;
        CheckPreCondition.notNull(mUID);
        if(bloodOxygen.getValue()<0.01)
            return result;
        if(bloodOxygen.getTime()==null)
            return result;
        bloodOxygen.setUID(mUID);
        try{
            result = bloodOxygenDao.insert(bloodOxygen);
        }catch (DuplicateKeyException e){
            log.debug(e.getMessage());
        }
        return result;
    }
    public int batchInsert(List<bloodOxygen> oxygenList,String mUID){
        CheckPreCondition.notNull(mUID);
        int result =0;
        for (bloodOxygen bloodOxygen : oxygenList) {
            result+=insert(bloodOxygen,mUID);
        }
        return result;
    }
    public List<bloodOxygen> query(String mUID,LocalDateTime from,LocalDateTime to){
        CheckPreCondition.notNull(mUID);
        CheckPreCondition.notNull(from);
        CheckPreCondition.notNull(to);
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return bloodOxygenDao.query(mUID, from.format(fm),to.format(fm));
    }
}
