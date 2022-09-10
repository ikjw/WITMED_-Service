package com.example.test.old.old_service.impl;

import com.example.test.old.old_dao.IInformationDao;
import com.example.test.old.entity.Information;
import com.example.test.old.old_service.IInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationService implements IInformationService {
    //@Autowired
    private IInformationDao iInformationDao;

    @Override
    public Information get(String username) {
        return iInformationDao.get(username);
    }

    @Override
    public int init(Information information){
        return iInformationDao.insert(information.getUsername(),information.getClientName(),information.getBirth(),information.getHeight1(),information.getWeight1()
                ,information.getDate(),information.getNum(),information.getAge(),information.getEnergy(),information.getAveenergy()
                ,information.getEnweight(),information.getLenweight(),information.getDiseases(), information.getFfm(), information.getMuscle(),
                information.getProtein(), information.getUcre());
    }

    @Override
    public int update(Information information) {
        return iInformationDao.update(information.getUsername(),information.getClientName(),information.getBirth(),information.getHeight1(),information.getWeight1()
        ,information.getDate(),information.getNum(),information.getAge(),information.getEnergy(),information.getAveenergy()
        ,information.getEnweight(),information.getLenweight(), information.getFfm(), information.getMuscle(),
                information.getProtein(), information.getUcre());
    }

    @Override
    public int updateDisease(String username, String diseases) {
        return iInformationDao.updatedis(username, diseases);
    }

    @Override
    public List<Information> getByDoctor(String doctorUsername){
        return iInformationDao.getByDoctor(doctorUsername);
    }

}
