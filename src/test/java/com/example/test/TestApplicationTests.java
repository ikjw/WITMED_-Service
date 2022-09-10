package com.example.test;

import com.example.test.old.old_service.impl.SleepService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TestApplicationTests {
    @Autowired
    private SleepService service;
    @Test
    void contextLoads() {
    }

}
