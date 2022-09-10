package com.example.test.controller;

import com.example.test.bean.account;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.accountService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/")
public class LoginController implements IPermission {
    @Resource
    accountService atService;
    @Resource
    envConfig config;
    /**
     * 登录功能
     * pre-condition：
     * account，password，type都不为空
     * post-condition：
     * 登录成功，将账号UID以及type存入session中；
     * 用户名不存在，返回100101错误码
     * 密码错误，返回100102错误码
     */
    @PostMapping("/login")
    public RespResult<?> login(@RequestBody Map<String,String> map, HttpSession session){
        RespResult<?> result;
        String at = map.getOrDefault("account",null);
        String password = map.getOrDefault("password",null);
        int type = -1;
        if(map.containsKey("type")){
            try{
                type = Integer.parseInt(map.get("type"));
            }catch (NumberFormatException e){
                type = -1;
            }
        }
        if(at!=null && password !=null && type != -1){
           account var = atService.login(at,type);
           if(var == null){
               //账号不存在
               result = new RespResult<>(100101,"账号不存在","账号不存在","", config.getEnv(), "");
           }else if(var.getPassword().equals(password)){
               //登录成功
               result = new RespResult<>(BaseRespResultCode.OK,"",config.getEnv(),"");
               session.setAttribute("UID",var.getUID());
               session.setAttribute("type",var.getType());
           }else{
               result = new RespResult<>(100102,"密码错误","密码错误","",config.getEnv(),"");
           }
        }else{
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
        }
        return result;
    }
}
