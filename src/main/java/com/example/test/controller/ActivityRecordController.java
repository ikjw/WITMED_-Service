package com.example.test.controller;

import com.example.test.bean.*;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.*;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import com.example.test.utils.fastJsonUtils;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("api/v2/activity")
@RestController
public class ActivityRecordController implements IPermission {
    @Resource
    envConfig config;
    @Resource
    stepRecordService stepService;
    @PostMapping("/step/add")
    public RespResult<?> stepAdd(@RequestBody List<stepRecord> records, HttpSession session){
        String UID = (String)session.getAttribute("UID");
        RespResult<?> result;
        if(records!=null){
            int success = 0;
            int total = 0;
            total = records.size();
            success = stepService.batchInsert(UID,records);
            Map<String,Object> map = new HashMap<>();
            map.put("total",total);
            map.put("success",success);
            result = new RespResult<>(BaseRespResultCode.OK,map,config.getEnv(),"");
        }else{
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"",config.getEnv(),"");
        }
        return result;
    }
    @PostMapping("/add")
    public RespResult<?> add(@RequestBody Map<String,Object>map,HttpSession session){
        String UID = (String)session.getAttribute("UID");
        String dataType = (String) map.get("dataType");
        Object data = map.get("data");
        RespResult<?> result;
        if(dataType==null || data == null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        int success = 0;
        int total = 0;
        switch (dataType) {
            case "step":
                try {
                    List<stepRecord> lst;
                    lst = fastJsonUtils.linkedMapTypeListToObjectList(data, stepRecord[].class);
                    total = lst.size();
                    success = stepService.batchInsert(UID,lst);

                } catch (Exception e) {
                    result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, "", config.getEnv(), "");
                    result.setDetailMessage(e.getMessage());
                    return result;
                }
                break;

            default:
                result = new RespResult<>(100201, "不支持该类型数据", "不支持该类型数据", "", config.getEnv(), "");
                return result;
        }
        Map<String,Object> resultInfo = new HashMap<>();
        resultInfo.put("success",success);
        resultInfo.put("total",total);
        result = new RespResult<>(BaseRespResultCode.OK,resultInfo, config.getEnv(), "");
        return result;
    }
    @PostMapping("/get")
    public RespResult<?> get(@RequestBody Map<String,Object>map,HttpSession session){
        String UID = (String)session.getAttribute("UID");
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataType = (String) map.get("dataType");
        String fromStr = (String) map.getOrDefault("from","2020-01-01 00:00:00");
        String toStr = (String) map.getOrDefault("to", LocalDateTime.now().format(fm));
        RespResult<?> result;
        int source = Integer.parseInt(map.get("source").toString());
        if(dataType==null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        LocalDateTime from;
        LocalDateTime to;
        try{
            from = LocalDateTime.parse(fromStr,fm);
            to = LocalDateTime.parse(toStr,fm);
        }catch (DateTimeParseException e){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return  result;
        }
        try{
            from = LocalDateTime.parse(fromStr,fm);
            to = LocalDateTime.parse(toStr,fm);
        }catch (DateTimeParseException e){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return  result;
        }
        switch (dataType) {
            case "step": {
                List<stepRecord> lst = stepService.queryWithSource(UID, from, to,source);
                result = new RespResult<>(BaseRespResultCode.OK, lst, config.getEnv(), "");
                break;
            }
            default:
                result = new RespResult<>(100201, "不支持该类型数据", "不支持该类型数据", "", config.getEnv(), "");
                break;
        }
        return result;
    }
}
