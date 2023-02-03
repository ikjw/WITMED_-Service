package com.example.test.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.test.bean.account;
import com.example.test.bean.doctorInfo;
import com.example.test.bean.userInfo;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.accountService;
import com.example.test.service.intf.doctorInfoService;
import com.example.test.service.intf.registerService;
import com.example.test.service.intf.userInfoService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/v2/admin/info")
@RestController
public class AdminInfoController implements IPermission {

    @Resource
    envConfig config;
    @Resource
    userInfoService userInfoService;
    @Resource
    doctorInfoService doctorInfoService;
    @Resource
    accountService accountService;

    @Resource
    registerService registerService;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Resp {
        private List<? extends userInfo> user = new ArrayList<>();
        private List<? extends doctorInfo> doctor = new ArrayList<>();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FullUserInfo extends userInfo {
        private String phone;
        private String mail;
        private int type;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime registerTime;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FullDoctorInfo extends doctorInfo {
        private String phone;
        private String mail;
        private int type;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime registerTime;
    }

    @Override
    public boolean hasPermission(String username, int role, String URI) {
        return role == 3;
    }

    @PostMapping("/getAll")
    public RespResult<?> getAll() {
        List<FullUserInfo> users = (List<FullUserInfo>) userInfoService.queryAll();
        List<FullDoctorInfo> doctors = (List<FullDoctorInfo>) doctorInfoService.queryAll();
        List<userInfo> usersNeedInsert = new ArrayList<>();
        List<doctorInfo> doctorsNeedInsert = new ArrayList<>();
        for (userInfo user : users) {
            // 用户不存在
            if (user.getName() == null) {
                user.setName("神秘用户");
                user.setGender(1);
                user.setBirthDay(LocalDate.of(2020, 1, 1));
                usersNeedInsert.add(user);
            }
        }
        userInfoService.batchInsert(usersNeedInsert);
        for (doctorInfo doctor : doctors) {
            // 医生不存在
            if (doctor.getName() == null) {
                doctor.setName("未知用户");
                doctor.setGender(0);
                doctorsNeedInsert.add(doctor);
            }
        }
        doctorInfoService.batchInsert(doctorsNeedInsert);

        return new RespResult<>(BaseRespResultCode.OK, new Resp(users, doctors), config.getEnv(), null);
    }

    @PostMapping("/update")
    public RespResult<?> update(@RequestBody JSONObject jsonObject) {
        if (!jsonObject.containsKey("type") || !jsonObject.containsKey("UID") || jsonObject.getString("UID") == null)
            return new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, "必须指定用户类型和UID", config.getEnv(), null);

        boolean needInsert = false;
        int result;

        // 更新phone、mail、password
        account at = accountService.login(jsonObject.getString("UID"), jsonObject.getInteger("type"));
        if (at == null)
            return new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, "用户不存在", config.getEnv(), null);
        if (jsonObject.containsKey("phone") && jsonObject.getString("phone") != null)
            at.setPhone(jsonObject.getString("phone"));
        if (jsonObject.containsKey("mail") && jsonObject.getString("mail") != null)
            at.setMail(jsonObject.getString("mail"));
        if (jsonObject.containsKey("password") && jsonObject.getString("password") != null)
            at.setPassword(jsonObject.getString("password"));
        result = accountService.update(at);

        // 患者
        if (jsonObject.getInteger("type") == 1) {
            userInfo user = userInfoService.query(jsonObject.getString("UID"));
            if (user == null) {
                user = new userInfo();
                needInsert = true;
            }
            if (jsonObject.containsKey("name") && jsonObject.getString("name") != null)
                user.setName(jsonObject.getString("name"));
            else if (needInsert)
                user.setName("神秘用户");
            if (jsonObject.containsKey("gender") && jsonObject.get("gender") != null && (jsonObject.getInteger("gender") & 0xFFFFFFFE) == 0)
                user.setGender(jsonObject.getInteger("gender"));
            else if (needInsert)
                user.setGender(1);
            if (jsonObject.containsKey("birthDay") && jsonObject.getString("birthDay") != null)
                user.setBirthDay(LocalDate.parse(jsonObject.getString("birthDay")));
            else if (needInsert)
                user.setBirthDay(LocalDate.of(2020, 1, 1));
            if (jsonObject.containsKey("faceBase64") && jsonObject.getString("faceBase64") != null)
                user.setFaceBase64(jsonObject.getString("faceBase64"));
            if (needInsert)
                result &= userInfoService.insert(user, jsonObject.getString("UID"));
            else
                result &= userInfoService.update(user);
        }
        // 医生
        else if (jsonObject.getInteger("type") == 2) {
            doctorInfo doctor = doctorInfoService.query(jsonObject.getString("UID"));
            if (doctor == null) {
                doctor = new doctorInfo();
                needInsert = true;
            }
            if (jsonObject.containsKey("name") && jsonObject.getString("name") != null)
                doctor.setName(jsonObject.getString("name"));
            else if (needInsert)
                doctor.setName("未知用户");
            if (jsonObject.containsKey("gender") && jsonObject.get("gender") != null && (jsonObject.getInteger("gender") & 0xFFFFFFFE) == 0)
                doctor.setGender(jsonObject.getInteger("gender"));
            else if (needInsert)
                doctor.setGender(0);
            if (jsonObject.containsKey("birthDay") && jsonObject.getString("birthDay") != null)
                doctor.setBirthDay(LocalDate.parse(jsonObject.getString("birthDay")));
            if (jsonObject.containsKey("faceBase64") && jsonObject.getString("faceBase64") != null)
                doctor.setFaceBase64(jsonObject.getString("faceBase64"));
            if (jsonObject.containsKey("domain") && jsonObject.getString("domain") != null)
                doctor.setDomain(jsonObject.getString("domain"));
            if (jsonObject.containsKey("profile") && jsonObject.getString("profile") != null)
                doctor.setProfile(jsonObject.getString("profile"));
            if (needInsert)
                result &= doctorInfoService.insert(doctor, jsonObject.getString("UID"));
            else
                result &= doctorInfoService.update(doctor);
        } else {
            return new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, "用户类型错误", config.getEnv(), null);
        }
        return new RespResult<>(BaseRespResultCode.OK, result, config.getEnv(), null);
    }

    @PostMapping("/get")
    public RespResult<?> get(@RequestBody Map<String, String> map) throws InvocationTargetException, IllegalAccessException {
        if (!map.containsKey("UID"))
            return new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, "必须指定UID", config.getEnv(), null);
        account accountInfo = accountService.queryByUIDWithoutPW(map.get("UID"));
        // 用户不存在
        if (accountInfo == null)
            return new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, "用户不存在", config.getEnv(), null);

        if (accountInfo.getType() == 1) {
            userInfo user = userInfoService.query(map.get("UID"));
            if (user == null) {
                user = new userInfo();
                user.setUID(map.get("UID"));
                user.setName("神秘用户");
                user.setGender(1);
                user.setBirthDay(LocalDate.of(2020, 1, 1));
                userInfoService.insert(user, map.get("UID"));
            }
            FullUserInfo resp = new FullUserInfo();
            BeanUtils.copyProperties(resp, user);
            resp.setMail(accountInfo.getMail());
            resp.setPhone(accountInfo.getPhone());
            resp.setType(accountInfo.getType());
            resp.setRegisterTime(accountInfo.getRegisterTime());
            return new RespResult<>(BaseRespResultCode.OK, resp, config.getEnv(), null);

        } else if (accountInfo.getType() == 2) {
            doctorInfo doctor = doctorInfoService.query(map.get("UID"));
            if (doctor == null) {
                doctor = new doctorInfo();
                doctor.setUID(map.get("UID"));
                doctor.setName("未知用户");
                doctor.setGender(0);
                doctorInfoService.insert(doctor, map.get("UID"));
            }
            FullDoctorInfo resp = new FullDoctorInfo();
            BeanUtils.copyProperties(resp, doctor);
            resp.setMail(accountInfo.getMail());
            resp.setPhone(accountInfo.getPhone());
            resp.setType(accountInfo.getType());
            resp.setRegisterTime(accountInfo.getRegisterTime());
            return new RespResult<>(BaseRespResultCode.OK, resp, config.getEnv(), null);
        } else {
            return new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, "用户类型错误", config.getEnv(), null);
        }
    }

    @PostMapping("/add")
    public RespResult<?> add(@RequestBody JSONObject jsonObject) {
        if (!jsonObject.containsKey("type") || !jsonObject.containsKey("phone") || !jsonObject.containsKey("password") || !jsonObject.containsKey("name") || !jsonObject.containsKey("gender") || (jsonObject.getInteger("gender") & 0xFFFFFFFE) != 0)
            return new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, null, config.getEnv(), null);

        account at = new account();

        // 生成UID
        String q = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime time = LocalDateTime.now();
        String UID = df.format(time) + q;

        at.setUID(UID);
        at.setType(jsonObject.getInteger("type"));
        at.setPhone(jsonObject.getString("phone"));
        at.setPassword(jsonObject.getString("password"));
        at.setRegisterTime(time);

        // 手机号已被注册
        if (accountService.login(at.getPhone(), at.getType()) != null)
            return new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, "手机号已被注册", config.getEnv(), null);

        if (registerService.addAccount(at) == 0) {
            return new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, "注册失败", config.getEnv(), null);
        }

        // 普通用户
        if (at.getType() == 1) {
            userInfo user = new userInfo();
            user.setName(jsonObject.getString("name"));
            user.setGender(jsonObject.getInteger("gender"));
            userInfoService.insert(user, at.getUID());
        }
        // 医生
        else if (at.getType() == 2) {
            doctorInfo doctor = new doctorInfo();
            doctor.setName(jsonObject.getString("name"));
            doctor.setGender(jsonObject.getInteger("gender"));
            doctorInfoService.insert(doctor, at.getUID());
        } else {
            return new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, "用户类型错误", config.getEnv(), null);
        }

        Map<String, String> result = new HashMap<>();
        result.put("UID", UID);
        return new RespResult<>(BaseRespResultCode.OK, result, config.getEnv(), null);
    }
}
