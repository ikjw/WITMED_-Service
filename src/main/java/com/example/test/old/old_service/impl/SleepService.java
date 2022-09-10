package com.example.test.old.old_service.impl;

import com.example.test.old.old_dao.ISleepDao;
import com.example.test.old.entity.datapointBean.sleepDataPoint;
import com.example.test.old.old_service.ISleepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SleepService implements ISleepService {

   // @Autowired
    private ISleepDao iSleepDao;

    @Override
    public int batchInsert(List<sleepDataPoint> lst) {

        if(lst.size()==0)
            return 0;
        lst.sort((o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime()));
        List<sleepDataPoint> dataBase = iSleepDao.query(lst.get(0).getUsername(),lst.get(0).getStartTime(),lst.get(lst.size()-1).getStartTime());
        List<sleepDataPoint> diff = new ArrayList<>();
        if(dataBase != null && dataBase.size()>0){
            dataBase.sort((o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime()));
            LocalDateTime time = null;
            for(sleepDataPoint i : lst){
                int index = findInsertIndex(dataBase,i);
                if(index != -1){
                    dataBase.add(index,i);
                    diff.add(i);
                }
            }
        }else {
            diff.addAll(lst);
        }
        for(sleepDataPoint i : diff){
            iSleepDao.insert(i.getUsername(),i.getType(),i.getStartTime(),i.getEndTime());
        }
        return diff.size();
    }
    private int findInsertIndex(List<sleepDataPoint> dataBase, sleepDataPoint sleepDataPoint){
        int ret = dataBase.size();
        for(int index=0;index<dataBase.size();index++){
            sleepDataPoint now = dataBase.get(index);
            if(sleepDataPoint.getStartTime().compareTo(now.getStartTime())>0){
                continue;
            }else if(sleepDataPoint.getStartTime().compareTo(now.getStartTime())==0){
                ret = -1;
                break;
            }else{
                if(sleepDataPoint.getEndTime().compareTo(now.getStartTime())<=0){
                    ret = index;
                }
            }
        }
        return ret;
    }

}