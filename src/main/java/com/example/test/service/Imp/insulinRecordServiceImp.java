package com.example.test.service.Imp;

import com.example.test.bean.insulinRecord;
import com.example.test.dao.insulinRecordDao;
import com.example.test.service.intf.insulinRecordService;
import com.example.test.utils.CheckPreCondition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class insulinRecordServiceImp implements insulinRecordService {
    @Resource
    insulinRecordDao dao;
    @Override
    public int insert(insulinRecord record, String mUID) {
        CheckPreCondition.notNull(mUID);
        if(record.getCount()<=0) return 0;
        if(record.getM()==null) return 0;
        if(record.getTime()==null) return 0;
        record.setUID(mUID);
        return dao.insert(record);
    }

    @Override
    public int batchInsert(List<insulinRecord> recordList, String mUID) {
        int ret = 0;
        for(insulinRecord record :recordList){
            ret += this.insert(record,mUID);
        }
        return ret;
    }

    @Override
    public List<insulinRecord> query(String mUID, LocalDateTime from, LocalDateTime to) {
        CheckPreCondition.notNull(mUID);
        CheckPreCondition.notNull(to);
        CheckPreCondition.notNull(from);
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dao.query(mUID,from.format(fm),to.format(fm));
    }
}
