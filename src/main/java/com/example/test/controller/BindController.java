package com.example.test.controller;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.doctorUserService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 *  该Controller所有人都能访问
 *  错误码范围：[100500,100599]
 **/
@RestController
@RequestMapping("/api/v2/bind/")
public class BindController implements IPermission {

    @Resource
    envConfig config;
    @Resource
    doctorUserService doctorUserService;
    @Override
    public boolean hasPermission(String username, int role, String URI) {
        return role == 2;
    }
    /**
     * 只有医生用户可以访问
     * {
     *     “uUID”:"xxxix"
     * }
     * 建立医生-患者的绑定关系
     */
    @PostMapping("/bind")
    public RespResult<?> bind(@RequestBody Map<String,String> du, HttpSession session){
        String dUID = (String) session.getAttribute("UID");
        String uUID = du.get("uUID");
        RespResult<?> result;
        if(uUID==null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        int success = doctorUserService.bind(dUID,uUID);
        if(success == 0)
        {
            result = new RespResult<>(100500,"已绑定的用户不能再次绑定","已绑定的用户不能再次绑定","", config.getEnv(), "");
        }
        else{
            result = new RespResult<>(BaseRespResultCode.OK,"", config.getEnv(), "");
        }
        return result;
    }

    /**
     * 只有医生用户可以访问
     * {
     *     “uUID”:"xxxix"
     * }
     * 取消医生-患者的绑定关系
     */
    @PostMapping("/unbind")
    public RespResult<?> unbind(@RequestBody Map<String,String> du, HttpSession session){
        String uUID = du.get("uUID");
        RespResult<?> result;
        if(uUID==null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        int success = doctorUserService.unbind(uUID);
        if(success==0){
            result = new RespResult<>(100501,"未绑定的用户不能再次绑定","未绑定的用户不能再次绑定","", config.getEnv(), "");
        }
        else{
            result = new RespResult<>(BaseRespResultCode.OK,"", config.getEnv(), "");
        }
        return result;
    }

    /**
     * 患者访问，获取与该患者绑定医生
     * 无请求体
     * 响应体:
     * {
     *     data:{
     *         "UID":"xxxxx",
     *         "UID":"",
     *         "name":"张三",
     *         "gender":1, //0女 1 男
     *         "birthday":"2019-01-05",
     *         "faceBase64":"",
     *         "domain":"",//专业领域
     *         "profile:""//个人简介
     *     }
     * }
     * @param session
     * @return
     */
    @PostMapping("/getDoctor")
    public RespResult<?> getDoctor(HttpSession session){
        return null;
    }

    /**
     * 医生访问，获取与该医生绑定的所有患者的基本信息
     * 无请求体
     * 响应体:
     * {
     *     data
     * }
     * @param session
     * @return
     */
    @PostMapping("/getPatient")
    public RespResult<?> getPatient(HttpSession session){
        return null;
    }

}
