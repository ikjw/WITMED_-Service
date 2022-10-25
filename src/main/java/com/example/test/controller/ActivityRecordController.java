package com.example.test.controller;

import com.example.test.bean.stepRecord;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.stepRecordService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
}
