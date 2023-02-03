package com.example.test.service.Imp;

import com.example.test.bean.doctorInfo;
import com.example.test.dao.doctorInfoDao;
import com.example.test.service.intf.doctorInfoService;
import com.example.test.utils.CheckPreCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class doctorInfoServiceImp implements doctorInfoService {
    @Resource
    doctorInfoDao doctorInfoDao;

    @Override
    public int insert(doctorInfo doctorInfo, String mUID) {
        int gender = doctorInfo.getGender();
        String name = doctorInfo.getName();
        CheckPreCondition.notNull(mUID);
        if (gender != 0 && gender != 1) return 0;
        if (name == null) return 0;
        doctorInfo.setUID(mUID);
        int result = 0;
        try {
            result = doctorInfoDao.insert(doctorInfo);
        } catch (DuplicateKeyException e) {
            log.debug(e.getMessage());
        }
        return result;
    }

    @Override
    public int batchInsert(List<? extends doctorInfo> doctorlist) {
        if (doctorlist.isEmpty())
            return 0;
        List<doctorInfo> newDoctorlist = new ArrayList<>();
        for (doctorInfo d : doctorlist) {
            if (d.getUID() == null || d.getName() == null)
                continue;
            if (d.getGender() != 0 && d.getGender() != 1)
                d.setGender(0);
            newDoctorlist.add(d);
        }
        int result = 0;
        try {
            result = doctorInfoDao.batchInsert(newDoctorlist);
        } catch (DuplicateKeyException e) {
            log.debug(e.getMessage());
        }
        return result;
    }

    @Override
    public doctorInfo query(String at) {
        CheckPreCondition.notNull(at);
        return doctorInfoDao.query(at);
    }

    @Override
    public List<? extends doctorInfo> queryAll() {
        return doctorInfoDao.queryAll();
    }

    @Override
    public int update(doctorInfo doctorInfo) {
        CheckPreCondition.notNull(doctorInfo);
        return doctorInfoDao.update(doctorInfo);
    }
}
