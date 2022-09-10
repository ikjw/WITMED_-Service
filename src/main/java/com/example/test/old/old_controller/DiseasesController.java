package com.example.test.old.old_controller;

import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.old.old_service.IDiseaseService;
import com.example.test.old.entity.Disease;
import com.example.test.utils.Imp.RespResult;
import com.example.test.utils.Imp.BaseRespResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/disease")
public class DiseasesController implements IPermission {
    @Autowired
    private IDiseaseService iDiseaseService;
    @Autowired
    private envConfig envConfig;
    @PostMapping(value = "/get")
    private RespResult<?> get(@RequestBody Map<String,String> map){
        RespResult<?> result;
        String keyword = map.getOrDefault("keyword","");
        String pageIndex = map.getOrDefault("pageIndex","1");
        Map<String,String> temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = map;
        }
        int index = 0;
        try{
            index = Integer.parseInt(pageIndex);
            List<Disease> lst = iDiseaseService.get(keyword,index);
            result = new RespResult<>(BaseRespResultCode.OK,lst,envConfig.getEnv(),"");
        }catch (NumberFormatException e){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp,envConfig.getEnv(),"");
        }
        return result;
    }
}