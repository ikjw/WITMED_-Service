package com.example.test.service.Imp;

import com.example.test.bean.sleep;
import com.example.test.bean.stepRecord;
import com.example.test.dao.bloodOxygenDao;
import com.example.test.dao.stepRecordDao;
import com.example.test.service.intf.stepRecordService;
import com.example.test.utils.CheckPreCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Service
@Slf4j
public class stepRecordServiceImp implements stepRecordService {
    @Resource
    stepRecordDao recordDao;
    @Override
    public int insert(String mUID, stepRecord record) {
        int result =0;
        CheckPreCondition.notNull(mUID);
        if(record.getSource()>1&&record.getSource()<0)
            return result;
        if(record.getStart()==null)
            return result;
        if(record.getEnd()==null)
            return result;
        record.setUID(mUID);
        try {
            result = recordDao.insert(record);
        }catch (DuplicateKeyException e)
        {
            log.debug(e.getMessage());
        }
        return result;
    }

    @Override
    public int batchInsert(String mUID, List<stepRecord> records) {
        CheckPreCondition.notNull(mUID);
        int result = 0;
        for (stepRecord record : records) {
            result +=insert(mUID,record);
        }
        return result;
    }
    @Override
    public List<stepRecord> query(String UID, LocalDateTime from,LocalDateTime to){
        CheckPreCondition.notNull(UID);
        CheckPreCondition.notNull(from);
        CheckPreCondition.notNull(to);
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return recordDao.query(UID, from.format(fm),to.format(fm));
    }
    @Override
    public List<stepRecord> queryWithSource(String UID, LocalDateTime from,LocalDateTime to,int source){
        CheckPreCondition.notNull(UID);
        CheckPreCondition.notNull(from);
        CheckPreCondition.notNull(to);
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return recordDao.queryWithSource(UID, from.format(fm),to.format(fm),source);
    }
}
