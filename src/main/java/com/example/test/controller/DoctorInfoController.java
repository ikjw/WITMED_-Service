package com.example.test.controller;

import com.example.test.bean.doctorInfo;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.doctorInfoService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;



/**
 *  该Controller只能由管理员或用户访问
 *  错误码范围：[100400,100499]
 **/
@RequestMapping("/api/v2/doctor/info")
@RestController
public class DoctorInfoController implements IPermission {
    @Override
    public boolean hasPermission(String username, int role, String URI) {
        return role == 2;
    }
    @Resource
    envConfig config;
    @Resource(name = "doctorInfoServiceImp")
    doctorInfoService doctorInfoService;
    /**
     * RequestBody:
     * {
     *   "name":"张三",
     *   "gender":1, //0女 1 男
     *   "birthday":"2019-01-05",
     *   "faceBase64":"",
     *   "domain":"",//专业领域
     *   "profile:""//个人简介
     * }
     * 响应体内data项无内容
     */
    @PostMapping("/add")
    public RespResult<?> add(@RequestBody doctorInfo info, HttpSession session){
        String UID =(String) session.getAttribute("UID");
        String name = info.getName();
        int gender = info.getGender();
        int success;
        RespResult<?> result;
        if((gender!=0&&gender!=1)||name==null)
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
        else {
            success = doctorInfoService.insert(info,UID);
            result = new RespResult<>(BaseRespResultCode.OK,success,config.getEnv(),"");
        }
        return result;
    }

    /**
     * 无请求体
     * ResponseBody:
     *{
     *   ....
     *   "data":
     *     {
     *       "UID":"",
     *       "name":"张三",
     *       "gender":1, //0女 1 男
     *       "birthday":"2019-01-05",
     *       "faceBase64":"",
     *       "domain":"",//专业领域
     *       "profile:""//个人简介
     *                }
     *   ....
     * }
     */
    @PostMapping("/get")
    public RespResult<?> get(HttpSession session){
        RespResult<?> result;
        String UID = (String) session.getAttribute("UID");
        doctorInfo info = doctorInfoService.query(UID);
        if (info == null) {
            info = new doctorInfo();
            info.setUID(UID);
            info.setName("未知用户");
            info.setGender(0);
            doctorInfoService.insert(info,UID);
        }
        result = new RespResult<>(BaseRespResultCode.OK,info,config.getEnv(), "");
        return result;
    }
    /**
     * RequestBody:
     * {
     *   "name":"张三",
     *   "gender":1, //0女 1 男
     *   "birthday":"2019-01-05",
     *   "faceBase64":"",
     *   "domain":"",//专业领域
     *   "profile:""//个人简介
     * }
     * 响应体内data项无内容
     */
    @PostMapping("/update")
    public RespResult<?> update(@RequestBody doctorInfo info, HttpSession session){
        String UID = (String) session.getAttribute("UID");
        doctorInfo doctorInfo = doctorInfoService.query(UID);
        info.setUID(UID);
        int i;
        RespResult<?> result;
        if(doctorInfo == null)
            i = doctorInfoService.insert(info,UID);
        else {
            if(info.getName()!=null)
            {
                doctorInfo.setName(info.getName());
            }
            if(info.getGender()==1||info.getGender()==0)
            {
                doctorInfo.setGender(info.getGender());
            }
            if(info.getBirthDay()!=doctorInfo.getBirthDay())
            {
                doctorInfo.setBirthDay(info.getBirthDay());
            }
            if(info.getFaceBase64()!=null)
            {
                doctorInfo.setFaceBase64(info.getFaceBase64());
            }
            if(info.getDomain()!=null)
            {
                doctorInfo.setDomain(info.getDomain());
            }
            if(info.getProfile()!=null)
            {
                doctorInfo.setProfile(info.getProfile());
            }
            i = doctorInfoService.update(doctorInfo);
        }
        result =new RespResult<>(BaseRespResultCode.OK,i, config.getEnv(), "");
        return result;
    }
}
