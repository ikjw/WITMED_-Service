package com.example.test.service.impl;

import com.example.test.dao.IFavorDao;
import com.example.test.entity.favor;
import com.example.test.service.IFavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavorService implements IFavorService {
    @Autowired
    private IFavorDao iFavorDao;

    @Override
    public int addfavor(String username, int foodid) {return iFavorDao.addfavor(username, foodid);}

    @Override
    public int deletefavor(String username, int foodid) {return iFavorDao.deletefavor(username, foodid);}

    @Override
    public List<favor> searchfavor(String username, int foodid) {return iFavorDao.searchfavor(username, foodid);}

    @Override
    public List<favor> favorlist(String username) {return iFavorDao.favorlist(username);}
}
