package com.example.test.controller;

import com.example.test.entity.Food;
import com.example.test.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@EnableAutoConfiguration
@RestController
@RequestMapping(value="/food")
public class FoodController {
@Autowired
    private IFoodService iFoodService;
@RequestMapping(value = "/login1", method = RequestMethod.POST)
    private List<Food> login1(@RequestParam String name){
    return iFoodService.login1(name);
    }
    @RequestMapping(value = "/login2", method = RequestMethod.POST)
    private List<Food> login2(@RequestParam String name){
        return iFoodService.login2(name);
    }
    @RequestMapping(value = "/login3", method = RequestMethod.POST)
    private List<Food> login3(@RequestParam String name){
        return iFoodService.login3(name);
    }
    @RequestMapping(value = "/login4", method = RequestMethod.POST)
    private List<Food> login4(@RequestParam String name){
        return iFoodService.login4(name);
    }
    @RequestMapping(value = "/login5", method = RequestMethod.POST)
    private List<Food> login5(@RequestParam String name){
        return iFoodService.login5(name);
    }
    @RequestMapping(value = "/login6", method = RequestMethod.POST)
    private List<Food> login6(@RequestParam String name){
        return iFoodService.login6(name);
    }
    @RequestMapping(value = "/login7", method = RequestMethod.POST)
    private List<Food> login7(@RequestParam String name){
        return iFoodService.login7(name);
    }
    @RequestMapping(value = "/idsearch", method = RequestMethod.POST)
    private List<Food> idsearch(@RequestParam int id){
        return iFoodService.idsearch(id);
    }
}
