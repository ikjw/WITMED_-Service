package com.example.test.service.intf;

import com.example.test.bean.dietPlan;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface dietPlanService {
    dietPlan queryRecent(String uUID);
    List<dietPlan> queryPlans(String uUID, LocalDate from, LocalDate to);
    int insert(dietPlan dietPlan);
}
