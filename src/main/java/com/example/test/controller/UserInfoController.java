package com.example.test.controller;

import com.example.test.bean.userInfo;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.userInfoService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import org.apache.commons.collections.set.PredicatedSet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v2/info")
public class UserInfoController implements IPermission {
    @Override
    public boolean hasPermission(String username, int role, String URI) {
        return role == 1 || role == 3;
    }
    @Resource
    envConfig config;
    @Resource
    userInfoService userInfoService;
    /**
     *
     * 获取用户个人信息，若对应用户的个人信息未初始化，但账号存在，
     * 则初始化用户信息为(“神秘用户”，“男”，“2020-01-01”，项目logo)
     */
    @PostMapping("/get")
    public RespResult<?> get(HttpSession session){
        RespResult<?> result;
        int i;
        String UID = (String) session.getAttribute("UID");
        userInfo userInfo = userInfoService.query(UID);
        if(userInfo == null)
        {
            userInfo.setName("神秘用户");
            userInfo.setUID(UID);
            userInfo.setGender(1);
            userInfo.setBirthDay(LocalDate.ofEpochDay(2020-01-01));
            i = userInfoService.insert(userInfo,UID);
        }
        result = new RespResult<>(BaseRespResultCode.OK,userInfo, config.getEnv(), "");
        return result;
    }

    /**
     * 初始化用户个人信息。
     */
    @PostMapping("/add")
    public RespResult<?> add(@RequestBody userInfo info, HttpSession session){
        String UID =(String) session.getAttribute("UID");
        String name = info.getName();
        int gender = info.getGender();
        int success;
        RespResult<?> result;
        if((gender!=0&&gender!=1)||name==null)
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
        else {
            success = userInfoService.insert(info,UID);
            result = new RespResult<>(BaseRespResultCode.OK,success,config.getEnv(),"");
        }
        return result;
    }

    /**
     *
     * 更新用户个人信息，若对应用户个人信息为初始化，则先初始化再更新。
     */
    @PostMapping("update")
    public RespResult<?> update(@RequestBody userInfo userInfo,HttpSession session){
        String UID =(String) session.getAttribute("UID");
        userInfo userInfo1 = userInfoService.query(UID);
        userInfo.setUID(UID);
        int i;
        RespResult<?> result;
        if(userInfo.getName()==null)
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
        userInfo.setUID(UID);
        if(userInfo1 ==null)
        {
            i = userInfoService.insert(userInfo,UID);
        }
        else
        {
            if(userInfo.getName()!=null)
                userInfo1.setName(userInfo.getName());
            if(userInfo.getGender()!=userInfo1.getGender())
                userInfo1.setGender(userInfo.getGender());
            if(userInfo.getBirthDay()!=null)
                userInfo1.setBirthDay(userInfo.getBirthDay());
            if(userInfo.getFaceBase64()!=null)
                userInfo1.setFaceBase64(userInfo.getFaceBase64());
            i = userInfoService.update(userInfo1);
        }
        result =new RespResult<>(BaseRespResultCode.OK,i, config.getEnv(), "");
        return result;
    }
}
