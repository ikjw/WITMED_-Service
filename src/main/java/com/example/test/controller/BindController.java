package com.example.test.controller;
import com.example.test.controller.intf.IPermission;
import com.example.test.utils.Imp.RespResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 *  该Controller所有人都能访问
 *  错误码范围：[100500,100599]
 **/
@RestController
@RequestMapping("/api/v2/bind/")
public class BindController implements IPermission {



    /**
     * 只有医生用户可以访问
     * {
     *     “uUID”:"xxxix"
     * }
     * 建立医生-患者的绑定关系
     */
    @PostMapping("/bind")
    public RespResult<?> bind(@RequestBody Map<String,String> du, HttpSession session){
        return  null;
    }

    /**
     * 只有医生用户可以访问
     * {
     *     “uUID”:"xxxix"
     * }
     * 取消医生-患者的绑定关系
     */
    @PostMapping("/unbind")
    public RespResult<?> unbind(@RequestBody Map<String,String> du, HttpSession session){
        return  null;
    }


}
