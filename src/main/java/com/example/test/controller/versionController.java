package com.example.test.controller;

import com.example.test.bean.version;
import com.example.test.config.envConfig;
import com.example.test.service.intf.versionService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/version")
public class versionController {
    @Resource
    envConfig config;
    @Resource
    versionService versionService;
    @PostMapping("/diff")
    public RespResult<?> differ(@RequestBody Map<String,Object> map, HttpSession session){
        String name = (String) map.get("name");
        int versionCode = (Integer) map.get("versionCode");
        RespResult<?> result;
        if (name==null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        version latestVersion = versionService.queryRecent(name);
        version nowVersion = versionService.queryVersion(versionCode,name);
        if (nowVersion == null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        List<version> diffVersion = versionService.query(versionCode,latestVersion.getVersionCode());
        int forceUpdate;
        if (latestVersion.getMaxCompatibleVersion()>versionCode)
            forceUpdate = 1;
        else forceUpdate = 0;
        Map<String,Object>map1=new HashMap<>();
        map1.put("latestVersion",latestVersion);
        map1.put("forceUpdate",forceUpdate);
        map1.put("diffVersion",diffVersion);
        result = new RespResult<>(BaseRespResultCode.OK,map1,config.getEnv(),"");
        return result;
    }
    @PostMapping("/add")
    public RespResult<?> add(@RequestBody version version,HttpSession session){
        RespResult<?> result;
        if (version.getVersionCode()==0||version.getVersionName()==null||version.getName()==null||version.getDescription()==null||version.getDetailDescription()==null||version.getMaxCompatibleVersion()==0){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        int success = versionService.insert(version);
        result = new RespResult<>(BaseRespResultCode.OK,success,config.getEnv(),"");
        return result;
    }
    @PostMapping("/latest")
    public RespResult<?> get(@RequestBody Map<String,String>map){
        RespResult<?> result;
        Map<String,Object> map1 = new HashMap<>();
        String name = map.get("name");
        if (name == null)
        {
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        version version = versionService.queryRecent(name);
        map1.put("versionCode",version.getVersionCode());
        map1.put("versionName",version.getVersionName());
        map1.put("url",config.getUrl());
        result = new RespResult<>(BaseRespResultCode.OK,map1,config.getEnv(),"");
        return result;
    }
}
