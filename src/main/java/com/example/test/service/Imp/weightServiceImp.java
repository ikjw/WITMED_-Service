package com.example.test.service.Imp;

import com.example.test.bean.weight;
import com.example.test.service.intf.weightService;

import java.time.LocalDate;
import java.util.List;

public class weightServiceImp implements weightService {
    @Override
    public int batchInsert(List<weight> weightList, String mUID) {
        return 0;
    }

    @Override
    public int insert(weight wt, String mUID) {
        return 0;
    }

    @Override
    public List<weight> query(String mUID, LocalDate from, LocalDate to) {
        return null;
    }
}
