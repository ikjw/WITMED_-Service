package com.example.test.controller;

import com.example.test.entity.Sleep;
import com.example.test.entity.Sport;
import com.example.test.service.ISleepService;
import com.example.test.service.ISportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sleep")
public class SleepController {
    @Autowired
    private ISleepService iSleepService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private Boolean add(@RequestBody Sleep sleep){
        return iSleepService.add(sleep.getId(), sleep.getLen(), sleep.getCdate(), sleep.getUser());
    }

    @RequestMapping(value = "/day", method = RequestMethod.POST)
    private List<Sleep> day(@RequestBody Sleep sleep) {
        return iSleepService.day(sleep.getUser(), sleep.getCdate());
    }
}
