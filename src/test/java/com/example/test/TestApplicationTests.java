package com.example.test;

import com.example.test.bean.insulinRecord;
import com.example.test.bean.medicine;

import com.example.test.bean.recipeRecommend;
import com.example.test.config.envConfig;
import com.example.test.service.intf.insulinRecordService;
import com.example.test.service.intf.medicineService;
import com.example.test.utils.fastJsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.test.utils.fastJsonUtils.jsonStrToObjectList;

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
        //System.out.println(config.getImgPath());
//        String str = "{\"breakfast\":[116206, 121477], \"lunch\":[116126, 109956], \"dinner\":[68688, 141899, 150633]}";
//        try {
//            recipeRecommend recipeRecommend = fastJsonUtils.jsonStrToObject(str,com.example.test.bean.recipeRecommend.class);
//            System.out.println(recipeRecommend);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
    }

}
