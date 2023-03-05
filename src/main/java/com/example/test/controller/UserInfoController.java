package com.example.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.test.bean.pregnancyInfo;
import com.example.test.bean.userInfo;
import com.example.test.bean.weight;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.heightService;
import com.example.test.service.intf.pregnancyInfoService;
import com.example.test.service.intf.userInfoService;
import com.example.test.service.intf.weightService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import org.apache.commons.collections.set.PredicatedSet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 *  该Controller只能由管理员或用户访问
 *  错误码范围：[100300,100399]
 **/
@RestController
@RequestMapping("/api/v2/user/info")
public class UserInfoController implements IPermission {

    @Override
    public boolean hasPermission(String username, int role, String URI) {
        return role == 1 || role == 3||role==4;
    }
    @Resource
    envConfig config;
    @Resource
    userInfoService userInfoService;
    @Resource
    weightService weightService;
    @Resource
    heightService heightService;
    @Resource
    pregnancyInfoService pregnancyInfoService;
    /**
     *
     * 获取用户个人信息，若对应用户的个人信息未初始化，但账号存在，
     * 则初始化用户信息为(“神秘用户”，“男”，“2020-01-01”，项目logo)
     */
    @PostMapping("/get")
    public RespResult<?> get(HttpSession session){
        RespResult<?> result;
        int i;
        String UID = (String) session.getAttribute("UID");
        userInfo userInfo = userInfoService.query(UID);
        if(userInfo == null)
        {
            userInfo = new userInfo();
            userInfo.setName("神秘用户");
            userInfo.setUID(UID);
            userInfo.setGender(1);
            userInfo.setBirthDay(LocalDate.ofEpochDay(2020-01-01));
            i = userInfoService.insert(userInfo,UID);
        }
        String type=session.getAttribute("type").toString();
        JSONObject userInfoWithType=(JSONObject)JSONObject.toJSON(userInfo);
        userInfoWithType.put("type",type);
        result = new RespResult<>(BaseRespResultCode.OK,userInfoWithType, config.getEnv(), "");
        return result;
    }

    /**
     * 初始化用户个人信息。
     */
    @PostMapping("/add")
    public RespResult<?> add(@RequestBody userInfo info, HttpSession session){
        String UID =(String) session.getAttribute("UID");
        String name = info.getName();
        int gender = info.getGender();
        int success;
        RespResult<?> result;
        if((gender!=0&&gender!=1)||name==null)
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
        else {
            success = userInfoService.insert(info,UID);
            result = new RespResult<>(BaseRespResultCode.OK,success,config.getEnv(),"");
        }
        return result;
    }

    /**
     *
     * 更新用户个人信息，若对应用户个人信息为初始化，则先初始化再更新。
     */
    @PostMapping("/update")
    public RespResult<?> update(@RequestBody userInfo userInfo,HttpSession session){
        String UID =(String) session.getAttribute("UID");
        userInfo userInfo1 = userInfoService.query(UID);
        userInfo.setUID(UID);
        int i;
        RespResult<?> result;
        if(userInfo.getName()==null) {
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, "", config.getEnv(), "");
            return result;
        }
        userInfo.setUID(UID);
        if(userInfo1 ==null)
        {
            i = userInfoService.insert(userInfo,UID);
        }
        else
        {
            if(userInfo.getName()!=null)
                userInfo1.setName(userInfo.getName());
            if(userInfo.getGender()!=userInfo1.getGender())
                userInfo1.setGender(userInfo.getGender());
            if(userInfo.getBirthDay()!=null)
                userInfo1.setBirthDay(userInfo.getBirthDay());
            if(userInfo.getFaceBase64()!=null)
                userInfo1.setFaceBase64(userInfo.getFaceBase64());
            i = userInfoService.update(userInfo1);
        }
        result =new RespResult<>(BaseRespResultCode.OK,i, config.getEnv(), "");
        return result;
    }
    @PostMapping("/init")
    public RespResult<?> init(@RequestBody Map<String,String>map, HttpSession session)
    {
        RespResult<?> result;
        int success = 0;
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String UID = (String) session.getAttribute("UID");
        String name = map.getOrDefault("name",null);
        String genderStr= map.getOrDefault("gender",null);
        String birthDayStr= map.getOrDefault("birthDay",null);
        String faceBase = map.getOrDefault("faceBase64",null);
        String weightStr = map.getOrDefault("weight",null);
        String heightStr = map.getOrDefault("height",null);
        String isPregnancyStr = map.getOrDefault("isPregnancy",null);
        if (name == null||genderStr == null||isPregnancyStr == null||heightStr==null||weightStr==null||birthDayStr==null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        LocalDate birthDay = LocalDate.parse(birthDayStr,fm);
        int gender = Integer.parseInt(genderStr);
        int isPregnancy = Integer.parseInt(isPregnancyStr);
        double weight = Double.parseDouble(weightStr);
        double height = Double.parseDouble(heightStr);
        if ((gender!=0&&gender!=1)||(isPregnancy!=0&&isPregnancy!=1)){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        userInfo userInfo = new userInfo(UID,name,gender,birthDay,faceBase);
        success+=userInfoService.insert(userInfo,UID);
        if (isPregnancy==1){
            String ppWeightStr = map.getOrDefault("ppWeight",null);
            String ppHeightStr = map.getOrDefault("ppHeight",null);
            String numberOfFetusesStr = map.getOrDefault("numberOfFetuses",null);
            String pDateStr = map.getOrDefault("pDate",null);
            String partiyStr = map.getOrDefault("partiy",null);
            if (ppHeightStr==null||ppWeightStr==null||numberOfFetusesStr==null||pDateStr==null||partiyStr==null)
            {
                result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
                return result;
            }
            double ppWeight = Double.parseDouble(ppWeightStr);
            double ppHeight = Double.parseDouble(ppHeightStr);
            int numberOfFetuses = Integer.parseInt(numberOfFetusesStr);
            int partiy = Integer.parseInt(partiyStr);
            LocalDate pDate;
            pDate = LocalDate.parse(pDateStr,fm);
            pregnancyInfo pregnancyInfo = new pregnancyInfo(UID,ppHeight,ppWeight,pDate,numberOfFetuses,partiy);
            success+=pregnancyInfoService.init(pregnancyInfo);
        }
        LocalDate time = LocalDate.now();
        weight wt = new weight(UID,weight,time,0,null);
        success+=weightService.insert(wt,UID);
        success+= heightService.init(UID,height,time);
        result = new RespResult<>(BaseRespResultCode.OK,success,config.getEnv(),"");
        return result;
    }
    @PostMapping("/pregUpdate")
    public RespResult<?> pregUpdate(@RequestBody pregnancyInfo pregnancyInfo, HttpSession session){
        String UID = (String) session.getAttribute("UID");
        RespResult<?> result;
        int success;
        pregnancyInfo pregnancyInfo1 = pregnancyInfoService.query(UID);
        pregnancyInfo.setUID(UID);
        if (pregnancyInfo1 == null)
        {
            success = pregnancyInfoService.init(pregnancyInfo);
        }
        else
        {
            if (pregnancyInfo.getPpHeight()!=0)
                pregnancyInfo1.setPpHeight(pregnancyInfo.getPpHeight());
            if (pregnancyInfo.getPpWeight()!=0)
                pregnancyInfo1.setPpWeight(pregnancyInfo.getPpWeight());
            if (pregnancyInfo.getPartiy()!=0)
                pregnancyInfo1.setPartiy(pregnancyInfo.getPartiy());
            if (pregnancyInfo.getPDate() != null)
                pregnancyInfo1.setPDate(pregnancyInfo.getPDate());
            if (pregnancyInfo.getNumberOfFetuses()!=0)
                pregnancyInfo1.setNumberOfFetuses(pregnancyInfo.getNumberOfFetuses());
            success = pregnancyInfoService.update(pregnancyInfo1);
        }
        result = new RespResult<>(BaseRespResultCode.OK,success,config.getEnv(),"");
        return result;
    }
    @PostMapping("/pregGet")
    public RespResult<?> pregGet(HttpSession session){
        RespResult<?> result;
        String UID = (String) session.getAttribute("UID");
        pregnancyInfo pregnancyInfo = pregnancyInfoService.query(UID);
        result = new RespResult<>(BaseRespResultCode.OK,pregnancyInfo,config.getEnv(),"");
        return result;
    }
}
