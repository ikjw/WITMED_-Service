package com.example.test.old.old_service.impl;

import com.example.test.old.old_dao.IDoctorInfoDao;
import com.example.test.old.old_dao.IDoctorPatientDao;
import com.example.test.old.entity.DoctorInfo;
import com.example.test.old.old_service.IDoctorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorInfoService implements IDoctorInfoService {
   // @Autowired
    private IDoctorInfoDao dao;

    //@Autowired
    private IDoctorPatientDao iDoctorPatientDao;

    @Override
    public DoctorInfo query(String userName) {
        return dao.query(userName);
    }

    @Override
    public int insert(DoctorInfo info) {
        return dao.insert(info.getUsername(),
                info.getName(),
                info.getSex(),
                info.getAge(),
                info.getDomain(),
                info.getProfile());
    }

    @Override
    public int update(DoctorInfo info) {
        return dao.update(info.getUsername(),
                info.getName(),
                info.getSex(),
                info.getAge(),
                info.getDomain(),
                info.getProfile());
    }

    @Override
    public int delete(String userName) {
        return dao.delete(userName);
    }

    @Override
    public List<DoctorInfo> getList() {
        return dao.getList();
    }

    @Override
    public int bind(String doctorUsername, String patientUsername) {
        return iDoctorPatientDao.bind(doctorUsername,patientUsername);
    }
}
