package com.example.test.controller;

import com.example.test.entity.CheckUsername;
import com.example.test.entity.Login;
import com.example.test.entity.USE;
import com.example.test.service.ILoginService;
import com.example.test.utils.MySessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    private ILoginService iLoginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private USE login(HttpServletRequest request, @RequestBody Login login) {
        try {
            HttpSession session = request.getSession(true);
            session.setMaxInactiveInterval(MySessionContext.getSessiontime());//有效时间30秒
            session.setAttribute("username", login.getUsername());
            String sessionID = session.getId();
            MySessionContext myc=MySessionContext.getInstance();
            myc.AddSession(session);
            Login x = iLoginService.login(login.getUsername(), login.getPassword());
            USE user = new USE();
            user.setRegister(x.getUsername() != null);
            user.setLogin(x);
            user.setSessionID(sessionID);
            return user;
        } catch (Exception e) {
            USE user = new USE();
            user.setLogin(new Login());
            user.setSessionID(null);
            user.setRegister(false);
            return user;
        }
    }

    @RequestMapping(value = "/loginuser", method = RequestMethod.POST)
    private CheckUsername loginuser(@RequestBody Login login) {
        Login temp = iLoginService.loginuser(login.getUsername(),login.getPassword(),login.getRole());
        CheckUsername result=new CheckUsername();
        if (temp==null) {//username不重复
            temp=new Login();
            result.setExist(false);
            result.setLogin(temp);
        } else {
            result.setLogin(temp);
            result.setExist(true);
        }
        return result;
    }

    @RequestMapping(value = "/loginDoctor", method = RequestMethod.POST)
    private Map<String,Object> loginDoctor(@RequestBody Login login){
        Login i  = iLoginService.query(login.getUsername());
        Map<String,Object> ret = new HashMap<>();
        if(i == null){
            ret.put("code",0);
        }else{
           if(i.equals(login)){
               ret.put("code",1);
           }else{
               ret.put("code",0);
           }
        }
        return ret;//1 登录成功 0 登录失败
    }
}

