package com.example.test.controller;

import com.example.test.bean.invitationCode;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.invitationCodeService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/")
public class InvitationCodeController implements IPermission {
    @Override
    public boolean hasPermission(String username, int role, String URI) {
        return role == 2;
    }
    @Resource
    envConfig config;
    @Resource
    com.example.test.service.intf.invitationCodeService invitationCodeService;
    @PostMapping("/createInvitationCode")
    public RespResult<?> invitationCode(HttpSession session){
        RespResult<?> result;
        String UID = (String) session.getAttribute("UID");
        String invitationCode = invitationCodeService.createCode(UID);
        result = new RespResult<>(BaseRespResultCode.OK,invitationCode, config.getEnv(),"");
        return result;
    }
    @PostMapping("/getInvitationCode")
    public RespResult<?> get(@RequestBody Map<String,Integer>map,HttpSession session){
        RespResult<?> result;
        int index = map.getOrDefault("pageIndex",1);
        int pageCount = map.getOrDefault("pageCount",20);
        if(index<1) index = 1;
        if(pageCount<=0) pageCount = 20;
        List<invitationCode> lst = invitationCodeService.query(index,pageCount);
        int success = invitationCodeService.deleteCode();
        result = new RespResult<>(BaseRespResultCode.OK,lst, config.getEnv(), "");
        return result;
    }
}
