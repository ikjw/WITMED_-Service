package com.example.test.controller;

import com.example.test.bean.userInfo;
import com.example.test.controller.intf.IPermission;
import com.example.test.utils.Imp.RespResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v2/info")
public class UserInfoController implements IPermission {
    /**
     *
     * 获取用户个人信息，若对应用户的个人信息未初始化，但账号存在，
     * 则初始化用户信息为(“神秘用户”，“男”，“2020-01-01”，项目logo)
     */
    @PostMapping("/get")
    public RespResult<?> get(HttpSession session){
        return null;
    }

    /**
     * 初始化用户个人信息。
     */
    @PostMapping("/add")
    public RespResult<?> add(userInfo info,HttpSession session){
        return null;
    }

    /**
     *
     * 更新用户个人信息，若对应用户个人信息为初始化，则先初始化再更新。
     */
    @PostMapping("update")
    public RespResult<?> update(userInfo info,HttpSession session){
        return null;
    }
}
