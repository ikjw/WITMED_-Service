package com.example.test.controller;

import com.example.test.bean.dietRecord;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.dietRecordService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RequestMapping("/api/v2/diet")
@RestController
public class DietController implements IPermission {
    @Resource
    envConfig config;
    @Resource
    dietRecordService dietService;

    @PostMapping("/add")
    public RespResult<?> get(@RequestBody dietRecord diet, HttpSession session){
        RespResult<?> result;
        Map<String,Integer> map = new HashMap<>();
        String UID = (String) session.getAttribute("UID");
        int type = (int) session.getAttribute("type");
        if(type !=1 && type != 3){
            result = new RespResult<>(BaseRespResultCode.PERMISSION_DENIED,config.getEnv(),"");
        }else if(diet.getAmount()<1 || diet.getAmount()>5 || diet.getType()<1 || diet.getType()>5 || diet.getStartTime()==null || diet.getEndTime() == null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,config.getEnv(),"");
        }else{
            map.put("total",1);
            map.put("success",dietService.insert(diet,UID));
            result = new RespResult<>(BaseRespResultCode.OK,map,config.getEnv(),"");
        }
        return result;
    }
    @PostMapping("/get")
    public RespResult<?> get(@RequestBody Map<String,String> map, HttpSession session){
        RespResult<?> result = null;
        String UID = (String) session.getAttribute("UID");
        int type = (int) session.getAttribute("type");
        String fromStr = map.get("from");
        String toStr = map.get("to");
        if(type !=1 && type != 3) {
            result = new RespResult<>(BaseRespResultCode.PERMISSION_DENIED, config.getEnv(), "");
        }else if(fromStr ==null || toStr == null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, config.getEnv(), "");
        }else{
            LocalDateTime from = null,to = null;
            DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            try{
                from = LocalDateTime.parse(fromStr,fm);
                to = LocalDateTime.parse(toStr,fm);
            }catch (Exception e){
                result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, config.getEnv(), "");
            }
            if(from != null && to != null){
                List<dietRecord> lst = dietService.query(UID,from,to);
                result = new RespResult<>(BaseRespResultCode.OK,lst, config.getEnv(), "");
            }
        }
        return result;
    }

}
