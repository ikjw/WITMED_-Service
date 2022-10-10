package com.example.test.controller;

import com.example.test.controller.intf.IPermission;
import com.example.test.old.entity.DoctorInfo;
import com.example.test.utils.Imp.RespResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
/**
 *  该Controller只能由管理员或用户访问
 *  错误码范围：[100400,100499]
 **/
@RequestMapping("/api/v2/doctor/info")
@RestController
public class DoctorInfoController implements IPermission {
    /**
     * RequestBody:
     * {
     *   "name":"张三",
     *   "gender":1, //0女 1 男
     *   "birthday":"2019-01-05",
     *   "faceBase64":"",
     *   "domain":"",//专业领域
     *   "profile:""//个人简介
     * }
     * 响应体内data项无内容
     */
    @PostMapping("/add")
    public RespResult<?> add(@RequestBody DoctorInfo info, HttpSession session){
        return null;
    }

    /**
     * 无请求体
     * ResponseBody:
     *{
     *   ....
     *   "data":
     *     {
     *       "UID":"",
     *       "name":"张三",
     *       "gender":1, //0女 1 男
     *       "birthday":"2019-01-05",
     *       "faceBase64":"",
     *       "domain":"",//专业领域
     *       "profile:""//个人简介
     *                }
     *   ....
     * }
     */
    @PostMapping("/get")
    public RespResult<?> get(HttpSession session){
        return null;
    }
    /**
     * RequestBody:
     * {
     *   "name":"张三",
     *   "gender":1, //0女 1 男
     *   "birthday":"2019-01-05",
     *   "faceBase64":"",
     *   "domain":"",//专业领域
     *   "profile:""//个人简介
     * }
     * 响应体内data项无内容
     */
    @PostMapping("/update")
    public RespResult<?> update(@RequestBody DoctorInfo info, HttpSession session){
        return null;
    }
}
