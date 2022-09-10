package com.example.test.old.old_controller;

import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.old.old_service.ISleepService;
import com.example.test.old.entity.datapointBean.sleepDataPoint;
import com.example.test.utils.Imp.RespResult;
import com.example.test.utils.Imp.BaseRespResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/sleep")
public class SleepController implements IPermission {
    @Autowired
    private ISleepService iSleepService;
    @Autowired
    envConfig config;
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private RespResult<?> add(@RequestBody List<sleepDataPoint> sleepDataPoint){
        RespResult<?> result;
        int count = iSleepService.batchInsert(sleepDataPoint);
        Map<String,Integer> map = new HashMap<>();
        map.put("count",count);
        result = new RespResult<>(BaseRespResultCode.OK,map,config.getEnv(),"");
        return  result;
    }
    @PostMapping("/error")
    private RespResult<?> error() throws Exception {
        throw new Exception("123");
    }
}
