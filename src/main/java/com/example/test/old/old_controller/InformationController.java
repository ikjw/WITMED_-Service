package com.example.test.old.old_controller;

import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.old.old_service.IInformationService;
import com.example.test.old.entity.Information;
import com.example.test.utils.Imp.RespResult;
import com.example.test.utils.Imp.BaseRespResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 该类的自定义 返回码范围为 100200 - 100299
 */

@Slf4j
@RestController
@RequestMapping(value = "/information")
public class InformationController implements IPermission {
    @Autowired
    private IInformationService iInformationService;
    @Autowired
    private envConfig envConfig;
    @PostMapping("/init")
    public RespResult<Information> init(@RequestBody Information info){
        RespResult<Information> respResult;
        Information temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = info;
        }
        if(info.getUsername()==null){
            respResult = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp,envConfig.getEnv(),"");
        }else{
            try{
                int code = iInformationService.init(info);
                respResult = new RespResult<>(info, envConfig.getEnv(), "");
            }catch (Exception e){
                respResult = new RespResult<>(100200,"信息已初始化，请勿重复提交",e.getMessage(),temp, envConfig.getEnv(), "");
            }
        }
        return respResult;
    }
    @PostMapping("/update")
    public RespResult<Information> update(@RequestBody Information info){
        RespResult<Information> respResult;
        Information temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = info;
        }
        if(info.getUsername()==null || info.getClientName()==null){
            respResult = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp,envConfig.getEnv(),"");
        }else{
            int code = iInformationService.update(info);
            if(code == 0){
                respResult = new RespResult<>(100201,"更新失败，请稍后重试","该用户不存在",temp, envConfig.getEnv(),"");
            }else{
                respResult = new RespResult<>(temp, envConfig.getEnv(), "");
            }
        }
        return respResult;
    }
    @PostMapping("/updateDisease")
    public RespResult<Information> updateDisease(@RequestBody Information info){
        RespResult<Information> respResult;
        Information temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = info;
        }
        if(info.getUsername()==null || info.getDiseases()==null){
            respResult = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp,envConfig.getEnv(),"");
        }else{
            int code = iInformationService.updateDisease(info.getUsername(),info.getDiseases());
            if(code == 0){
                respResult = new RespResult<>(100201,"更新失败，请稍后重试","该用户不存在",temp, envConfig.getEnv(),"");
            }else{
                respResult = new RespResult<>(temp, envConfig.getEnv(), "");
            }
        }
        return respResult;
    }
    @PostMapping("/get")
    public RespResult<Information> get(@RequestBody Information info){
        RespResult<Information> respResult;
        Information temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = info;
        }
        if(info.getUsername()==null){
            respResult = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp,envConfig.getEnv(),"");
        }else{
            Information information = iInformationService.get(info.getUsername());
            if(information == null){
                respResult = new RespResult<>(100201,"未初始化信息，请先初始化信息","该用户不存在",temp, envConfig.getEnv(),"");
            }else{
                respResult = new RespResult<>(information, envConfig.getEnv(), "");
            }
        }
        return respResult;
    }
    @PostMapping("/getByDoctor")
    public RespResult<List<Information>> get(@RequestBody Map<String,Object> map){
        RespResult<List<Information>> respResult;
        String doctorUsername = (String) map.get("doctorUsername");
        if(doctorUsername==null){
            respResult = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,null,envConfig.getEnv(),"");
        }else{
            List<Information> informations = iInformationService.getByDoctor(doctorUsername);
            respResult = new RespResult<>(informations, envConfig.getEnv(), "");
        }
        return respResult;
    }


}