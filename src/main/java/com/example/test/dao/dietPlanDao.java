package com.example.test.dao;

import com.example.test.bean.dietPlan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface dietPlanDao {
    dietPlan queryRecent(String uUID);
    List<dietPlan> queryPlans(String uUID, String from, String to);
    int insert(dietPlan dietPlan);
}
