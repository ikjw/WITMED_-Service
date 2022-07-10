package com.example.test.controller;

import com.example.test.entity.Login;
import com.example.test.utils.MySessionContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/logout")
public class LogoutConroller {

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    private Login Logout(HttpServletRequest request, @RequestBody Login login) {
        Login temp = new Login();
        String username = login.getUsername();
        MySessionContext myc = MySessionContext.getInstance();
        String sessionid = request.getHeader("Cookie");
        HttpSession session = myc.getSession(sessionid);
        if (session == null) {//无效会话，登录过期，退出
            temp.setUsername("!@#");
            temp.setRole(-100);
        } else {
            String usertmp = (String) session.getAttribute("username");
            if (usertmp.equals(username)) {//登录状态，退出
                temp.setUsername("!@!");
                temp.setRole(100);
            } else {
                temp.setRole(-100);
                temp.setUsername("###");
            }
        }
        return temp;
    }
}
