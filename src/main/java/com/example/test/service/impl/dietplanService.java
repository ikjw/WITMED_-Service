package com.example.test.service.impl;

import com.example.test.dao.IdietplanDao;
import com.example.test.entity.dietplan;
import com.example.test.service.IdietplanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class dietplanService implements IdietplanService {
    @Autowired
    IdietplanDao dao;
    @Override
    public int add(dietplan plan) {
        return dao.add(plan.getTargetUsername(), plan.getDoctorUsername(), plan.getStart(),
                plan.getEnd(), plan.getTotalEnergy(), plan.getTotalFood(), plan.getMainFood(),
                plan.getVegetables(),plan.getFruit(),plan.getMeatAndEgg(),plan.getBean(),plan.getMilk(),
                plan.getNut(),plan.getOil(),plan.getSalt());
    }

    @Override
    public List<dietplan> queryByPatient(String targetUsername) {
        return dao.queryByPatient(targetUsername);
    }

    @Override
    public List<dietplan> queryByDoctor(String doctorUsername) {
        return dao.queryByDoctor(doctorUsername);
    }

    @Override
    public List<dietplan> queryByDoctorAndPatient(String targetUsername, String doctorUsername) {
        return dao.queryByDoctorAndPatient(targetUsername, doctorUsername);
    }
}
