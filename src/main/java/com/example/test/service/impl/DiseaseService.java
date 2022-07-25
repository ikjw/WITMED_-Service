package com.example.test.service.impl;

import com.example.test.dao.IDiseaseDao;
import com.example.test.entity.Disease;
import com.example.test.service.IDiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiseaseService implements IDiseaseService {
    @Autowired
    private IDiseaseDao iDiseaseDao;

    @Override
    public List<Disease> get(String keyword,int pageIndex){
        return iDiseaseDao.get(keyword,(pageIndex-1)*50,50);
    }
}
