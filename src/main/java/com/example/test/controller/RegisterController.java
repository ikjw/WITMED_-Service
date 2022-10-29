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
import java.text.SimpleDateFormat;
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
    public RespResult<?> send(@RequestBody Map<String,String> map,HttpSession session)
    {
        RespResult<?> result;
        String phone = map.get("phone");
        if(phone==null)
        {
            result = new RespResult<>(101005,"手机号不能为空","手机号不能为空","", config.getEnv(), "");
            return result;
        }
        String test =registerService.sendMs(phone);
        session.setAttribute("code",test);
        result = new RespResult<>(BaseRespResultCode.OK,test, config.getEnv(),"");
        return result;
    }
    /**
     * RequestBody:
     * {
     *    'phone':'******'
     *    ......
     * }
     * 因为只能用一个手机
     * */
    @PostMapping("/register")
    public RespResult<?> register(@RequestBody Map<String,String> map, HttpSession session){
        RespResult<?> result;
        int p  = (int)((Math.random()*9+1)*1000);
        String q = String.valueOf(p);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);
        String UID = localTime+q;
        String password = map.getOrDefault("password",null);
        String code = map.get("code");
        String realCode = (String) session.getAttribute("code");
        String phone = map.getOrDefault("phone",null);
        int type = -1;
        if(map.containsKey("type")){
            try{
                type = Integer.parseInt(map.get("type"));
            }catch (NumberFormatException e){
                type = -1;
            }
        }
        if (phone == null)
        {
            result = new RespResult<>(101001,"手机号不能为空","手机号不能为空","", config.getEnv(), "");
            return result;
        }
        if (password == null)
        {
            result = new RespResult<>(101002,"密码不能为空","密码不能为空","", config.getEnv(), "");
            return result;
        }
        if(atService.login(phone,type)!=null)
        {
            result = new RespResult<>(101003,"此手机号已被绑定","此手机号已被绑定","", config.getEnv(), "");
            return result;
        }
        if (!code.equals(realCode))
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
        session.setAttribute("UID",UID);
        session.setAttribute("type",type);
        result = new RespResult<>(BaseRespResultCode.OK,"", config.getEnv(),"");
        return result;
    }
}
