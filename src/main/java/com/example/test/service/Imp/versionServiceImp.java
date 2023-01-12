package com.example.test.service.Imp;

import com.example.test.bean.version;
import com.example.test.dao.versionDao;
import com.example.test.service.intf.versionService;
import com.example.test.utils.CheckPreCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class versionServiceImp implements versionService {
    @Resource
    versionDao versionDao;
    @Override
    public version queryRecent(String name){
        CheckPreCondition.notNull(name);
        return versionDao.queryRecent(name);
    }
    @Override
    public version queryVersion(int versionCode,String name){
        return versionDao.queryVersion(versionCode,name);
    }
    @Override
    public int insert(version version){
        CheckPreCondition.notNull(version);
        return versionDao.insert(version);
    }
    @Override
    public List<version> query(int start, int end,String name){
        return versionDao.query(start,end,name);
    }
    public version queryVersionById(int id){
        return versionDao.queryVersionById(id);
    }
    @Override
    public int uploadFile(int id, String filename){
        CheckPreCondition.notNull(filename);
        return versionDao.uploadFile(id,filename);
    }
}

