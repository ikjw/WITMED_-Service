package com.example.test;

import com.example.test.bean.insulinRecord;
import com.example.test.bean.medicine;

import com.example.test.config.envConfig;
import com.example.test.service.intf.insulinRecordService;
import com.example.test.service.intf.medicineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class TestApplicationTests {
    @Autowired
    insulinRecordService insulin;
    @Autowired
    medicineService service;
    @Autowired
    envConfig config;
    @Test
    void contextLoads() {
//        List<medicine> lst= service.query("胰岛素",1,20);
//        insulinRecord record = new insulinRecord("2022090522411111",lst.get(0),1, LocalDateTime.now(),0,"123");
//        insulin.insert(record,"2022090522411111");
        System.out.println(config.getImgPath());
    }

}
