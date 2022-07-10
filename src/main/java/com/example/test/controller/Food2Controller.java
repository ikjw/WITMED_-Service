package com.example.test.controller;

import com.example.test.entity.Food2;
import com.example.test.service.IFood2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/food2")
public class Food2Controller {
    @Autowired
    private IFood2Service iFood2Service;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private Boolean add(@RequestBody Food2 food2){
        return iFood2Service.add(food2.getUser(), food2.getId(), food2.getCal(), food2.getCdate(), food2.getTime_slot());
    }

    @RequestMapping(value = "/day", method = RequestMethod.POST)
    private List<Food2> day(@RequestBody Food2 food2) {
        return iFood2Service.day(food2.getUser(), food2.getCdate());
    }
}
