package com.example.test;

import com.example.test.entity.DoctorInfo;
import com.example.test.entity.Login;
import com.example.test.entity.Weight;
import com.example.test.service.impl.DoctorInfoService;
import com.example.test.service.impl.LoginService;
import com.example.test.service.impl.RegisterService;
import com.example.test.service.impl.WeightService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class TestApplicationTests {
    @Autowired
    private WeightService service;
    @Test
    void contextLoads() {
       List<Weight> lst =  service.getWeightList("1584643004");
       Weight weight= service.getRecentWeight("1584643004");
    }

}
