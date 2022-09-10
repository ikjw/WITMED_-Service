package com.example.test.old.old_controller;

import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.old.old_service.ILoginService;
import com.example.test.old.entity.Login;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


/**
 * 该类的自定义 返回码范围为 100100 - 100199
 */
public class LoginController implements IPermission {
    @Autowired
    private ILoginService iLoginService;
    @Autowired
    private envConfig envConfig;
    @PostMapping("/login")
    private RespResult<String> login(@RequestBody Login login, HttpSession session) {
        RespResult<String> result;
        if(login.getUsername()!=null && login.getPassword()!=null && login.getRole()!=null){
            int code = iLoginService.login(login);
            if(code == 1){
                result = new RespResult<>("",envConfig.getEnv(),"");
                session.setAttribute("username",login.getUsername());
                session.setAttribute("role",login.getRole());
            }else{
                result = new RespResult<>(100101,"账号或密码错误","账号或密码错误","",envConfig.getEnv(),"");
            }
        }else{
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", envConfig.getEnv(), "");
        }
        return result;
    }
    @PostMapping("/register")
    private RespResult<String> register(@RequestBody Login login){
        RespResult<String> result;
        if(login.getUsername()!=null && login.getPassword()!=null && login.getRole()!=null){
            int code = iLoginService.register(login);
            if(code == 1){
                result = new RespResult<>("",envConfig.getEnv(),"");
            }else{
                result = new RespResult<>(100102,"用户名重复","用户名重复","",envConfig.getEnv(),"");
            }
        }else{
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", envConfig.getEnv(), "");
        }
        return result;
    }
}

