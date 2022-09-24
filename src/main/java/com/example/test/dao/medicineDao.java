package com.example.test.dao;

import com.example.test.bean.medicine;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface medicineDao {
    List<medicine> query(String keyWord,int ignoreNum,int pageCount);

    medicine queryBymID(int mID);
}
