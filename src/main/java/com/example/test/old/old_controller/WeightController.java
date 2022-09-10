package com.example.test.old.old_controller;

import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.old.entity.Weight;
import com.example.test.old.old_service.IWeightService;
import com.example.test.utils.Imp.RespResult;
import com.example.test.utils.Imp.BaseRespResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 该类的自定义 返回码范围为 100400 - 100499
 */
@RestController
@RequestMapping(value = "/weight")
public class WeightController implements IPermission {

    @Autowired
    private IWeightService iWeightService;
    @Autowired
    private envConfig envConfig;
    /**
     * 若数据库内存在的数据日期与当前新增数据的日期相同，之前的数据将会被覆盖且无法恢复
     * @param weight 三个字段都不能为null
     * @return
     */
    @PostMapping("/add")
    private RespResult<Weight> add(@RequestBody Weight weight) {
        RespResult<Weight> result;
        Weight temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = weight;
        }
        if(weight.getUsername()==null || weight.getWeight() == null || weight.getDate() == null ){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp,envConfig.getEnv(),"");
        }else{
            iWeightService.add(weight.getUsername(), weight.getWeight(), weight.getDate());
            result = new RespResult<>(BaseRespResultCode.OK,temp,envConfig.getEnv(),"");
        }
        return result;
    }

    /**
     *
     * 获取指定用户一段时间内的体重，如果startDate为空，则获取endDate及之前的所有记录，如果endDate为空，则获取startDate及之后的所有记录，如果都为空则获取所有的记录。
     * @return username的所有体重记录，
     *         没有查询到返回 “[]”
     */
    @PostMapping("/get")
    private RespResult<?> getWeightList(@RequestBody Map<String,String> map) {
        RespResult<?> result = null;
        Map<String,String> temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = map;
        }
        if(!map.containsKey("username") || map.get("username")==null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp,envConfig.getEnv(),"");
        }else{
            Date startDate,endDate;
            try{
                startDate = Date.valueOf(map.getOrDefault("startDate", "2020-01-01"));
                endDate =  Date.valueOf(map.getOrDefault("endDate", LocalDate.now().toString()));
                List<Weight> lst = iWeightService.getWeightList(map.get("username"),startDate,endDate);
                result = new RespResult<>(BaseRespResultCode.OK,lst, envConfig.getEnv(), "");
            }catch (IllegalArgumentException e){
                result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp,envConfig.getEnv(),"");
            }
        }
        return result;
    }

    /**
     *
     * @param
     * @return username对应的最近的一次体重记录
     */
    @PostMapping("/getRecent")
    private RespResult<?> getRecentWeight(@RequestBody Weight weight) {
        RespResult<Weight> result;
        Weight temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = weight;
        }
        if(weight.getUsername() == null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp,envConfig.getEnv(),"");
        }else{
            Weight weight1 = iWeightService.getRecentWeight(weight.getUsername());
            result = new RespResult<>(BaseRespResultCode.OK,weight1, envConfig.getEnv(), "");
        }
        return result;
    }
}
