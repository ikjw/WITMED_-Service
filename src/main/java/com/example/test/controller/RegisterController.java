package com.example.test.controller;

import com.example.test.bean.account;
import com.example.test.config.envConfig;
import com.example.test.service.intf.accountService;
import com.example.test.service.intf.registerService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 *  该Controller 所有人都能访问
 **/
@RestController
@RequestMapping("/api/v2/")
public class RegisterController {
    @Resource
    registerService registerService;
    @Resource
    accountService atService;
    @Resource
    envConfig config;
/**
 * RequestBody:
 * {
 *    'phone':'******'
 * }
 * 因为只能用一个手机
 * */
    @PostMapping("/send")
    public RespResult<?> send(@RequestBody Map<String,String> map)
    {
        RespResult<?> result;
        String phone = map.get("phone");
        if(phone==null)
        {
            result = new RespResult<>(101005,"手机号不能为空","手机号不能为空","", config.getEnv(), "");
            return result;
        }
        String test =registerService.sendMs(phone);
        result = new RespResult<>(BaseRespResultCode.OK,test, config.getEnv(),"");
        return result;
    }
    /**
     * RequestBody:
     * {
     *    'UID':
     *    'phone':'******'
     *    ......
     * }
     * 因为只能用一个手机
     * */
    @PostMapping("/register")
    public RespResult<?> register(@RequestBody Map<String,String> map, HttpSession session){
        RespResult<?> result;
        String UID = map.getOrDefault("UID",null);
        String password = map.getOrDefault("password",null);
        String code = map.get("code");
        int type = -1;
        if(map.containsKey("type")){
            try{
                type = Integer.parseInt(map.get("type"));
            }catch (NumberFormatException e){
                type = -1;
            }
        }
        if (UID == null)
        {
            result = new RespResult<>(101001,"账号不能为空","账号不能为空","", config.getEnv(), "");
            return result;
        }
        if (password == null)
        {
            result = new RespResult<>(101002,"密码不能为空","密码不能为空","", config.getEnv(), "");
            return result;
        }
        if(atService.login(UID,type)!=null)
        {
            result = new RespResult<>(101003,"用户名被使用","用户名被使用","", config.getEnv(), "");
            return result;
        }
        if (!code.equals(registerService.getCode()))
        {
            result = new RespResult<>(101004,"验证码错误","验证码错误","", config.getEnv(), "");
            return result;
        }
        account account =new account();
        account.setMail(map.get("mail"));
        account.setType(type);
        account.setPassword(password);
        account.setWechatId(map.get("wechatId"));
        account.setUID(UID);
        account.setPhone(map.get("phone"));
        account.setRegisterTime(LocalDateTime.now());
        registerService.addAccount(account);
        result = new RespResult<>(BaseRespResultCode.OK,account, config.getEnv(),"");
        return result;
    }
}
