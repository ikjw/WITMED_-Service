package com.example.test.old.old_controller;

import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.old.old_service.IFoodService;
import com.example.test.old.entity.Food;
import com.example.test.utils.Imp.RespResult;
import com.example.test.utils.Imp.BaseRespResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/food")
public class FoodController implements IPermission {
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
