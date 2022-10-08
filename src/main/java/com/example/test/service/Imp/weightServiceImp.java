package com.example.test.service.Imp;

import com.example.test.bean.bloodSugar;
import com.example.test.bean.weight;
import com.example.test.dao.bloodSugarDao;
import com.example.test.dao.weightDao;
import com.example.test.old.old_dao.IWeightDao;
import com.example.test.service.intf.weightService;
import com.example.test.utils.CheckPreCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Slf4j
@Service
public class weightServiceImp implements weightService {
    @Resource
    weightDao weightDao;
    @Override
    public int batchInsert(List<weight> weightList, String mUID) {
        CheckPreCondition.notNull(mUID);
        int count = 0;
        for(weight weight : weightList){
            count+=insert(weight,mUID);
        }
        return count;
    }

    @Override
    public int insert(weight wt, String mUID) {
        CheckPreCondition.notNull(mUID);
        if(wt.getValue()<=0.00001) return 0;
        if(wt.getDate()==null) return 0;
        wt.setUID(mUID);
        int result = 0;
        try{
            result = weightDao.insert(wt);
        }catch (DuplicateKeyException e){
            log.debug(e.getMessage());
        }
        return result;
    }

    @Override
    public List<weight> query(String mUID, LocalDate from, LocalDate to) {
        CheckPreCondition.notNull(mUID);
        CheckPreCondition.notNull(to);
        CheckPreCondition.notNull(from);
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return weightDao.query(mUID,from.format(fm),to.format(fm));
    }
    @Override
    public weight queryRecent(String mUID){
        CheckPreCondition.notNull(mUID);
        return weightDao.queryRecent(mUID);
    }
}
