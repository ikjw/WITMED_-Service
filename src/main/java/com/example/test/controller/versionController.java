package com.example.test.controller;

import com.example.test.bean.version;
import com.example.test.config.envConfig;
import com.example.test.service.intf.versionService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        List<version> diffVersion = versionService.query(versionCode,latestVersion.getVersionCode(),name);
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
        // 仅管理员有权发新版本
        if(session.getAttribute("UID") == null || (Integer) session.getAttribute("type") != 3){
            return new RespResult<>(BaseRespResultCode.PERMISSION_DENIED,"",config.getEnv(),"");
        }

        RespResult<?> result;
        if (version.getVersionCode()==0||version.getVersionName()==null||version.getName()==null||version.getDescription()==null||version.getDetailDescription()==null||version.getMaxCompatibleVersion()==0){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        int success = versionService.insert(version);
        version version1 = versionService.queryVersion(version.getVersionCode(), version.getName());
        result = new RespResult<>(BaseRespResultCode.OK,version1,config.getEnv(),"");
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
        String url=version.getFile();
        if (url!=null){
            url=config.getApkBaseUrl()+url;
        }
        else {
            url="";
        }
        map1.put("url",url);
        result = new RespResult<>(BaseRespResultCode.OK,map1,config.getEnv(),"");
        return result;
    }
    @PostMapping("upload_file")
    public RespResult<?> uploadFile(int id, MultipartFile file,HttpSession session){
        // 仅管理员有权上传文件
        if(session.getAttribute("UID") == null || (Integer) session.getAttribute("type") != 3){
            return new RespResult<>(BaseRespResultCode.LOGIN_TIMEOUT,"",config.getEnv(),"");
        }
        System.out.println("OK");
        version version=versionService.queryVersionById(id);
        if (version==null){
            return new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"该id不存在",config.getEnv(),"");
        }

        // apk命名
        String name=version.getName();
        String versionName = version.getVersionName();
        String filename=null;
        versionName =  versionName.replace(".","_");
        if (name.equals("医生端(Android)")){
            filename="doctor_"+versionName+".apk";
        }
        else if (name.equals("患者端(Android)")){
            filename="client_"+versionName+".apk";
        }

        // 写到本地
        String savePath = config.getApkPath()+filename;
        try {
            file.transferTo(new java.io.File(savePath));
        } catch (Exception e) {
            e.printStackTrace();
            return new RespResult<>(BaseRespResultCode.SYS_EXCEPTION,"apk文件保存失败",config.getEnv(),"");
        }

        // 更新数据库
        int success = versionService.uploadFile(id,filename);
        return new RespResult<>(BaseRespResultCode.OK,success,config.getEnv(),"");
    }
}
