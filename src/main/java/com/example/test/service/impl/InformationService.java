package com.example.test.service.impl;

import com.example.test.dao.IInformationDao;
import com.example.test.entity.Information;
import com.example.test.service.IInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationService implements IInformationService {
    @Autowired
    private IInformationDao iInformationDao;

    @Override
    public Information get(String username) {
        return iInformationDao.get(username);
    }

    @Override
    public void addnew(Information information){
        iInformationDao.addnew(information.getUsername(),information.getClientName(),information.getBirth(),information.getHeight1(),information.getWeight1()
                ,information.getDate(),information.getNum(),information.getAge(),information.getEnergy(),information.getAveenergy()
                ,information.getEnweight(),information.getLenweight(),information.getDiseases());
    }

    @Override
    public void update(Information information) {
        iInformationDao.update(information.getUsername(),information.getBirth(),information.getHeight1(),information.getWeight1()
        ,information.getDate(),information.getNum(),information.getAge(),information.getEnergy(),information.getAveenergy()
        ,information.getEnweight(),information.getLenweight());
    }

    @Override
    public void updatedis(String username, String diseases) {
        iInformationDao.updatedis(username, diseases);
    }

    @Override
    public Information getdis(String username) {
        return iInformationDao.getdis(username);
    }

    public List<Information> getPatientByDoctor(String doctorUsername){return iInformationDao.getPatientByDoctor(doctorUsername);}
    @Override
    public List<Information> getAll(){return iInformationDao.getAll();}
}
