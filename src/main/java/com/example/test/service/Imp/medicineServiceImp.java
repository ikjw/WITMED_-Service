package com.example.test.service.Imp;

import com.example.test.bean.medicine;
import com.example.test.dao.medicineDao;
import com.example.test.service.intf.medicineService;
import com.example.test.utils.CheckPreCondition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class medicineServiceImp implements medicineService {
    @Resource
    medicineDao  dao;

    @Override
    public List<medicine> query(String keyWord, int pageIndex,int pageCount) {
        CheckPreCondition.notNull(keyWord);
        if(pageIndex<1) throw new RuntimeException("pageIndex>=1 ！");
        if(pageCount<=0) throw new RuntimeException("pageCount>0 !");
        return dao.query(keyWord,(pageIndex-1)*pageCount,pageCount);
    }
    @Override
    public medicine queryBymID(int mID) {
        return dao.queryBymID(mID);
    }
}
