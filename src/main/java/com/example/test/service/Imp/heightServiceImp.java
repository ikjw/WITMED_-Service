package com.example.test.service.Imp;

import com.example.test.bean.height;
import com.example.test.dao.heightDao;
import com.example.test.service.intf.heightService;
import com.example.test.utils.CheckPreCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class heightServiceImp implements heightService {
    @Resource
    heightDao heightDao;
    @Override
    public int init(String UID, double height, LocalDate time){
        CheckPreCondition.notNull(UID);
        CheckPreCondition.notNull(time);
        if (height<100)
            return 0;
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return heightDao.init(UID,height,time.format(fm));
    }
    @Override
    public height queryRecent(String UID){
        CheckPreCondition.notNull(UID);
        return heightDao.queryRecent(UID);
    }
}
