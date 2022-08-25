package com.example.test.controller;

import com.example.test.configBean.envConfig;
import com.example.test.entity.Food;
import com.example.test.service.IFoodService;
import com.example.test.utils.RespResult;
import com.example.test.utils.enums.BaseRespResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private envConfig envConfig;

    @PostMapping("/get")
    public RespResult<?> get(@RequestBody Map<String,String> map){
        RespResult<?> result;
        Map<String,String> temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = map;
        }
        String keyword = map.getOrDefault("keyword","");
        String pageIndex = map.getOrDefault("pageIndex","1");
        try{
            int index = Integer.parseInt(pageIndex);
            List<Food> lst = iFoodService.get(keyword,index);
            result = new RespResult<>(BaseRespResultCode.OK,lst,envConfig.getEnv(),"");
        }catch (NumberFormatException e){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp,envConfig.getEnv(),"");
        }
        return result;
    }
    @PostMapping("/getById")
    public RespResult<?> getById(@RequestBody Map<String,String> map){
        RespResult<?> result;
        Map<String,String> temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = map;
        }
        String strId = map.getOrDefault("id","##");
        try{
            int id = Integer.parseInt(strId);
            Food food = iFoodService.getById(id);
            result = new RespResult<>(BaseRespResultCode.OK,food, envConfig.getEnv(), "");
        }catch (NumberFormatException e){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp, envConfig.getEnv(), "");
        }
        return  result;
    }
}
