package com.example.test.controller;

import com.example.test.dao.IDoctorInfoDao;
import com.example.test.entity.DoctorInfo;
import com.example.test.entity.Food;
import com.example.test.service.IDoctorInfoService;
import com.example.test.service.IFavorService;
import com.example.test.service.impl.DoctorInfoService;
import com.example.test.utils.MySessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableAutoConfiguration
@RestController
@RequestMapping(value="/doctorInfo")
public class DoctorInfoController {
    @Autowired
    private IDoctorInfoService service;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private Map<String,Object> add(@RequestBody DoctorInfo info){
        DoctorInfo i = service.query(info.getUsername());
        Map<String,Object> ret = new HashMap<>();
        if(i != null){
            ret.put("code",-1);//已存在
        }else{
            ret.put("code",service.insert(info));
        }
        return ret;
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    private DoctorInfo get(@RequestParam String username){
        return service.query(username);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    private Map<String,Object> update(@RequestBody DoctorInfo info){
        Map<String,Object> ret = new HashMap<>();
        ret.put("code",service.update(info));
        return ret;//1 成功 0 失败
    }

    @RequestMapping("/list")
    public List<DoctorInfo> getList(){
        return service.getList();
    }
    /**
     * 患者绑定医生
     * @param doctorUsername
     * @param patientUsername
     * @return
     */
    @PostMapping("/bind")
    public int bind(@RequestParam("doctorUsername") String doctorUsername,@RequestParam("patientUsername") String patientUsername){
        return service.bind(doctorUsername,patientUsername);
    }

}
