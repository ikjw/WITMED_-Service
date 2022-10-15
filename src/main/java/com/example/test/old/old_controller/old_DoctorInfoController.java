package com.example.test.old.old_controller;

import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.old.entity.DoctorInfo;
import com.example.test.old.old_service.IDoctorInfoService;
import com.example.test.utils.Imp.RespResult;
import com.example.test.utils.Imp.BaseRespResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 本类自定义的结果码范围为100300~100399
 */
@RestController
@RequestMapping(value="/doctorInfo")
public class old_DoctorInfoController implements IPermission {
    @Autowired
    private IDoctorInfoService service;

    @Autowired
    private envConfig envConfig;
    @PostMapping("/init")
    private RespResult<DoctorInfo> init(@RequestBody DoctorInfo info){
        RespResult<DoctorInfo> result;
        DoctorInfo temp = null;
        if(envConfig.getEnv().equals("dev"))
            temp = info;
        if(info.getUsername()==null && info.getName()==null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp,envConfig.getEnv(),"");
        }else{
            try{
                int code = service.insert(info);
                result = new RespResult<>(BaseRespResultCode.OK,temp, envConfig.getEnv(), "");
            }catch (DataAccessException e){
                result = new RespResult<>(100300,"请勿重复提交",e.getMessage(),temp, envConfig.getEnv(), "");
            }
        }
        return result;
    }

    @PostMapping("/get")
    private RespResult<DoctorInfo> get(@RequestBody DoctorInfo info){
        RespResult<DoctorInfo> result;
        DoctorInfo temp = null;
        if(envConfig.getEnv().equals("dev"))
            temp = info;
        if(info.getUsername()==null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp,envConfig.getEnv(),"");
        }else{
            DoctorInfo doctorInfo = service.query(info.getUsername());
            if(doctorInfo == null){
                result = new RespResult<>(100301,"请先初始化个人信息","用户信息不存在",temp,envConfig.getEnv(),"");
            }else{
                result = new RespResult<>(BaseRespResultCode.OK,doctorInfo, envConfig.getEnv(), "");
            }
        }
        return result;
    }

    /**
     * 覆盖式更新
     * @param info
     * @return
     */
    @PostMapping("/update")
    private RespResult<DoctorInfo> update(@RequestBody DoctorInfo info){
        RespResult<DoctorInfo> result;
        DoctorInfo temp = null;
        if(envConfig.getEnv().equals("dev"))
            temp = info;
        if(info.getUsername()==null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp,envConfig.getEnv(),"");
        }else{
            int code = service.update(info);
            if(code == 1){
                result = new RespResult<>(BaseRespResultCode.OK,temp, envConfig.getEnv(), "");
            }else{
                result = new RespResult<>(100301,"请先初始化个人信息","用户信息不存在",temp,envConfig.getEnv(),"");
            }
        }
        return result;
    }

    @RequestMapping("/getAll")
    public RespResult<List<DoctorInfo>> getList(){
        RespResult<List<DoctorInfo>> result;
        List<DoctorInfo> infos = service.getList();
        result = new RespResult<>(BaseRespResultCode.OK,infos, envConfig.getEnv(), "");
        return result;
    }

    /**
     * 患者绑定医生
     * @return
     */
    @PostMapping("/bind")
    public RespResult<String> bind(@RequestBody Map<String,String> map){
        RespResult<String> result;
        String temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = map.toString();
        }
        if(map.containsKey("doctorUsername") && map.containsKey("patientUsername")){
            try{
                int code = service.bind(map.get("doctorUsername"),map.get("patientUsername"));
                result = new RespResult<>(BaseRespResultCode.OK,temp, envConfig.getEnv(), "");
            }catch (DataAccessException e){
                result = new RespResult<>(100302,"已有绑定的医生",e.getMessage(),temp, envConfig.getEnv(), "");
            }
        }else{
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp,envConfig.getEnv(),"");
        }
        return result;
    }

}
