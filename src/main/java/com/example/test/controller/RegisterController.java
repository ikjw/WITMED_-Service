package com.example.test.controller;

import com.example.test.entity.DoctorInfo;
import com.example.test.entity.Login;
import com.example.test.service.ILoginService;
import com.example.test.service.IRegisterService;
import com.example.test.service.impl.DoctorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping(value = "/login")
public class RegisterController {
    @Autowired
    private IRegisterService iRegisterService;
    @Autowired
    private DoctorInfoService doctorInfoService;
    @Autowired
    private ILoginService loginService;
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    private void register(@RequestBody Login register) {
        iRegisterService.register(register.getUsername(), register.getPassword());
    }
    @RequestMapping(value = "/register/doctor", method = RequestMethod.POST)
    private Map<String,Object> registerDoctor(@RequestBody Map<String,Object> registerInfo) {
        Map<String,Object> ret = new HashMap<>();
        String[] Keys = {"username","password","role","id","name","sex","age","domain","profile"};
        for(String i : Keys){
            if(!registerInfo.containsKey(i)){
                ret.put("code",-2);//缺少参数
                return ret;
            }
        }
        Login login  = loginService.query((String) registerInfo.get("username"));
        if(login != null){
            ret.put("code",-1);//重复
            return ret;
        }
        DoctorInfo info = new DoctorInfo();
        info.setUsername((String) registerInfo.get("username"));
        info.setName((String) registerInfo.get("name"));
        info.setDomain((String) registerInfo.get("domain"));
        info.setProfile((String) registerInfo.get("profile"));
        info.setId((Integer) registerInfo.get("id"));
        info.setSex((Integer) registerInfo.get("sex"));
        info.setAge((Integer) registerInfo.get("age"));
        int loginRet = iRegisterService.registerDoctor(
                        (String)registerInfo.get("username"),
                        (String)registerInfo.get("password"),
                        (Integer)registerInfo.get("role"));
        if(loginRet != 1){
            ret.put("code",loginRet);
        }else{
            int doctorRet = doctorInfoService.insert(info);
            if(doctorRet == -1) doctorRet = doctorInfoService.update(info);
            ret.put("code",doctorRet+loginRet);
        }
        return ret;//2 成功 1 注册成功 信息初始化失败  0 注册失败
    }

}

