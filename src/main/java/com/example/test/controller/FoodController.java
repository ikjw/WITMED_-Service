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

@RestController
@RequestMapping(value="/food")
public class FoodController {
    @Autowired
    private IFoodService iFoodService;
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
