package com.example.test.service.Imp;

import com.example.test.bean.dietPlan;
import com.example.test.dao.dietPlanDao;
import com.example.test.service.intf.dietPlanService;
import com.example.test.utils.CheckPreCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class dietPlanServiceImp implements dietPlanService {
    @Resource
    dietPlanDao dietPlanDao;
    @Override
    public dietPlan queryRecent(String uUID){
        CheckPreCondition.notNull(uUID);
        dietPlan dp = dietPlanDao.queryRecent(uUID);
        LocalDate date = LocalDate.now();
        int i = dp.getEndDate().compareTo(date);
        if (i < 0)
            return null;
        else return  dp;
    }
    @Override
    public List<dietPlan> queryPlans(String uUID, LocalDate from,LocalDate to){
        CheckPreCondition.notNull(uUID);
        CheckPreCondition.notNull(to);
        CheckPreCondition.notNull(from);
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dietPlanDao.queryPlans(uUID,from.format(fm),to.format(fm));
    }
    @Override
    public int insert(dietPlan dietPlan){
        CheckPreCondition.notNull(dietPlan);
        return dietPlanDao.insert(dietPlan);
    }
}
