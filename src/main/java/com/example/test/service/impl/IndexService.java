package com.example.test.service.impl;

import com.example.test.dao.IIndexDao;
import com.example.test.entity.Index;
import com.example.test.service.IIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexService implements IIndexService {
    @Autowired
    private IIndexDao iIndexDao;

    @Override
    public void add(Index index) {
        iIndexDao.add(index.getId(), index.getIcf(), index.getEcf(), index.getMuscle(), index.getProtein(),
                index.getFfweight(), index.getPerfat(), index.getBrate());
    }

    @Override
    public Index search(Index index){
        return iIndexDao.search(index.getId());
    }

    @Override
    public void update(Index index){
        iIndexDao.update(index.getId(), index.getIcf(), index.getEcf(), index.getMuscle(), index.getProtein(),
                index.getFfweight(), index.getPerfat(), index.getBrate());
    }
}
