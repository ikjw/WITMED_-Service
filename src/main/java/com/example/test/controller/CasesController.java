package com.example.test.controller;


import com.example.test.bean.cases;
import com.example.test.bean.recipe;
import com.example.test.config.envConfig;
import com.example.test.service.intf.casesService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import net.sf.json.JSONArray;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/cases")
public class CasesController {
    @Resource
    envConfig config;
    @Resource
    casesService casesService;
    @PostMapping("/upload")
    public RespResult<?> get(@RequestBody Map<String,String> map, HttpSession session){
        String UID = (String) session.getAttribute("UID");
        String base64 = map.get("base64");
        RespResult<?> result;
        if (base64 == null)
        {
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        int success = casesService.insert(UID,base64, LocalDateTime.now());
        result = new RespResult<>(BaseRespResultCode.OK,success, config.getEnv(), "");
        return result;
    }
    @PostMapping("/get")
    public RespResult<?> get( HttpSession session){
        String UID = (String) session.getAttribute("UID");
        RespResult<?> result;
        List<cases> cases = casesService.query(UID);
        for (cases aCase : cases) {
            aCase.setImg((String) JSONArray.fromObject(aCase.getImg()).get(0));
        }
        result = new RespResult<>(BaseRespResultCode.OK,cases, config.getEnv(), "");
        return result;
    }
    @PostMapping("/getUser")
    public RespResult<?> getUser(@RequestBody Map<String,String>map,HttpSession session){
        String UID = map.get("UID");
        RespResult<?> result;
        List<cases> cases = casesService.query(UID);
        for (cases aCase : cases) {
            aCase.setImg((String) JSONArray.fromObject(aCase.getImg()).get(0));
        }
        result = new RespResult<>(BaseRespResultCode.OK,cases, config.getEnv(), "");
        return result;
    }
}
