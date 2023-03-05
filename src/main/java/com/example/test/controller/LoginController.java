package com.example.test.controller;

import com.example.test.bean.account;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.accountService;
import com.example.test.service.intf.doctorUserService;
import com.example.test.service.intf.invitationCodeService;
import com.example.test.service.intf.registerService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
/**
 *  该Controller 所有人都能访问
 *  错误码范围：[100100,100199]
 **/
@RestController
@RequestMapping("/api/v2/")
public class LoginController implements IPermission {

    @Resource
    accountService atService;
    @Resource
    registerService registerService;
    @Resource
    envConfig config;
    @Resource
    invitationCodeService invitationCodeService;
    @Resource
    doctorUserService doctorUserService;
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
                result = new RespResult<>(100101,"账号不存在","账号不存在",null, config.getEnv(), "");
            }else if(var.getPassword().equals(password)){
                //登录成功
                Map<String,String> tmp = new HashMap<>();
                tmp.put("UID",var.getUID());
                result = new RespResult<>(BaseRespResultCode.OK,tmp,config.getEnv(),"");
                session.setAttribute("UID",var.getUID());
                session.setAttribute("type",var.getType());
                session.setMaxInactiveInterval(60*60*24*14);
            }else{
                result = new RespResult<>(100102,"密码错误","密码错误",null,config.getEnv(),"");
            }
        }else{
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,null, config.getEnv(),"");
        }
        return result;
    }

    @PostMapping("/send")
    public RespResult<?> send(@RequestBody Map<String,String> map,HttpSession session)
    {
        RespResult<?> result;
        String phone = map.get("phone");
        if(phone==null)
        {
            result = new RespResult<>(101005,"手机号不能为空","手机号不能为空",null, config.getEnv(), "");
            return result;
        }
        String test =registerService.sendMs(phone);
        session.setAttribute("code",test);
        session.setMaxInactiveInterval(60*30);
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
        //String code = map.get("code");
        //String realCode = (String) session.getAttribute("code");
        String invitationCode = map.get("invitationCode");
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
            result = new RespResult<>(101001,"手机号不能为空","手机号不能为空",null, config.getEnv(), "");
            return result;
        }
        if (password == null)
        {
            result = new RespResult<>(101002,"密码不能为空","密码不能为空",null, config.getEnv(), "");
            return result;
        }
        if(atService.login(phone,type)!=null)
        {
            result = new RespResult<>(101003,"此手机号已被绑定","此手机号已被绑定",null, config.getEnv(), "");
            return result;
        }
        if (type!=4) {
            int success = invitationCodeService.findCode(UID,invitationCode);
            if (success == 0) {
                result = new RespResult<>(101004, "验证码错误", "验证码错误", null, config.getEnv(), "");
                return result;
            }
            String dUID = invitationCodeService.queryDoctor(invitationCode, UID).getDUID();
            doctorUserService.bind(dUID, UID);
        }
        account account =new account();
        account.setMail(map.getOrDefault("mail",null));
        account.setType(type);
        account.setPassword(password);
        account.setWechatId(map.getOrDefault("wechatId",null));
        account.setUID(UID);
        account.setPhone(map.get("phone"));
        account.setRegisterTime(LocalDateTime.now());
        registerService.addAccount(account);
        session.setAttribute("UID",UID);
        session.setAttribute("type",type);
        session.setMaxInactiveInterval(60*60*24*14);
        Map<String,String> ret = new HashMap<>();
        ret.put("UID",UID);
        result = new RespResult<>(BaseRespResultCode.OK,ret, config.getEnv(),"");
        return result;
    }
    @PostMapping("/modifyPwd")
    public RespResult<?> modifyPwd(@RequestBody Map<String,String>map,HttpSession session){
        RespResult<?> result;
        String UID = (String) session.getAttribute("UID");
        String oldPsw = map.get("oldPassword");
        String newPsw = map.get("newPassword");
        if (oldPsw == null||newPsw == null)
        {
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,null, config.getEnv(),"");
            return result;
        }
        int success = atService.updatePsw(UID,oldPsw,newPsw);
        result = new RespResult<>(BaseRespResultCode.OK,success, config.getEnv(),"");
        return result;
    }
    @PostMapping("/change")
    public RespResult<?> change(@RequestBody Map<String,String>map,HttpSession session) {
        RespResult<?> result;
        String code = map.get("invitationCode");
        String UID = (String) session.getAttribute("UID");
        int success = invitationCodeService.findCode(UID,code);
        if (success == 1){
            String dUID = invitationCodeService.queryDoctor(code, UID).getDUID();
            doctorUserService.bind(dUID, UID);
            atService.updateType("UID");
            result = new RespResult<>(BaseRespResultCode.OK,success, config.getEnv(),"");
        }
        else {
            result = new RespResult<>(101004, "验证码错误", "验证码错误", null, config.getEnv(), "");
        }
        return result;
    }

    @PostMapping("/logout")
    public RespResult<?> logout(HttpSession session){
        RespResult<?> result;
        session.invalidate();
        result = new RespResult<>(BaseRespResultCode.OK,null, config.getEnv(),"");
        return result;
    }
}
