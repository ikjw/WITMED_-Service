package com.example.test.controller;

import com.example.test.utils.Imp.RespResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  该Controller患者无权访问，只有管理员和医生用户可以访问
 *  医生访问患者权限说明：
 *                  医生只能够访问与自己绑定的患者的信息，无权访问未绑定的患者的相关信息
 */
@RequestMapping("/api/v2/observe/")
@RestController
public class ObserveController {

    @PostMapping("/health")
    public RespResult<?> getHealthData(){
        return null;
    }
}
