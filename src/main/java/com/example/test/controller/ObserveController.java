package com.example.test.controller;

import com.example.test.bean.*;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.*;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import net.sf.json.JSONArray;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  该Controller患者无权访问，只有管理员和医生用户可以访问
 *  医生访问患者权限说明：
 *                  医生只能够访问与自己绑定的患者的信息，无权访问未绑定的患者的相关信息
 */
@RequestMapping("/api/v2/observe/")
@RestController
public class ObserveController implements IPermission {
    @Override
    public boolean hasPermission(String username,int role,String URI){
        return role == 2||role == 3;
    }
    @Resource
    envConfig config;
    @Resource
    bloodSugarService sugarService;
    @Resource
    com.example.test.service.intf.weightService weightService;
    @Resource(name = "insulinRecordServiceImp")
    insulinRecordService insulinService;
    @Resource
    com.example.test.service.intf.heartRateService heartRateService;
    @Resource(name = "sleepServiceImp")
    com.example.test.service.intf.sleepService sleepService;
    @Resource
    bloodOxygenService bloodOxygenService;
    @Resource
    doctorUserService doctorUserService;
    @Resource
    stepRecordService stepRecordService;
    @Resource
    dietRecordService dietRecordService;
    @PostMapping("/health")
    public RespResult<?> getHealthData(@RequestBody Map<String,String> map, HttpSession session){
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dUID = (String) session.getAttribute("UID");
        String uUID = map.get("uUID");
        String dataType = map.get("dataType");
        String fromStr = map.getOrDefault("from","2020-01-01 00:00:00");
        String toStr = map.getOrDefault("to", LocalDateTime.now().format(fm));
        RespResult<?> result;
        if (doctorUserService.Find(dUID,uUID) == 0){
            result = new RespResult<>(BaseRespResultCode.PERMISSION_DENIED,"", config.getEnv(),"");
            return result;
        }
        if(dataType==null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        LocalDateTime from;
        LocalDateTime to;
        try{
            from = LocalDateTime.parse(fromStr,fm);
            to = LocalDateTime.parse(toStr,fm);
        }catch (DateTimeParseException e){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return  result;
        }
        if(dataType.equals("bloodSugar")){
            List<bloodSugar> lst = sugarService.query(uUID,from,to);
            result = new RespResult<>(BaseRespResultCode.OK,lst, config.getEnv(), "");
        }else if(dataType.equals("weight")){
            LocalDate new_from = from.toLocalDate();
            LocalDate new_to = to.toLocalDate();
            List<weight> lst = weightService.query(uUID,new_from,new_to);
            result = new RespResult<>(BaseRespResultCode.OK,lst, config.getEnv(), "");
        }else if(dataType.equals("insulinRecord")){
            List<insulinRecord> lst = insulinService.query(uUID,from,to);
            result = new RespResult<>(BaseRespResultCode.OK,lst, config.getEnv(), "");
        }else if (dataType.equals("heartRate")) {
            List<heartRate> lst = heartRateService.query(uUID,from,to);
            result = new RespResult<>(BaseRespResultCode.OK,lst, config.getEnv(), "");
        } else if (dataType.equals("sleep")) {
            List<sleep> lst = sleepService.query(uUID,from,to);
            result = new RespResult<>(BaseRespResultCode.OK,lst, config.getEnv(), "");
        }else if (dataType.equals("bloodOxygen")) {
            List<bloodOxygen> lst = bloodOxygenService.query(uUID,from,to);
            result = new RespResult<>(BaseRespResultCode.OK,lst, config.getEnv(), "");
        } else{
            result = new RespResult<>(100201,"不支持该类型数据","不支持该类型数据","", config.getEnv(), "");
        }
        return result;
    }
    @PostMapping("/sports")
    public RespResult<?>getSports(@RequestBody Map<String,String>map,HttpSession session){
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dUID = (String) session.getAttribute("UID");
        String uUID = map.get("uUID");
        String dataType = map.get("dataType");
        String fromStr = map.getOrDefault("from","2020-01-01 00:00:00");
        String toStr = map.getOrDefault("to", LocalDateTime.now().format(fm));
        RespResult<?> result;
        if (doctorUserService.Find(dUID,uUID) == 0){
            result = new RespResult<>(BaseRespResultCode.PERMISSION_DENIED,"", config.getEnv(),"");
            return result;
        }
        if(dataType==null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        LocalDateTime from;
        LocalDateTime to;
        try{
            from = LocalDateTime.parse(fromStr,fm);
            to = LocalDateTime.parse(toStr,fm);
        }catch (DateTimeParseException e){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return  result;
        }
        if (dataType.equals("step"))
        {
            List<stepRecord> lst = stepRecordService.query(uUID,from,to);
            result = new RespResult<>(BaseRespResultCode.OK,lst, config.getEnv(), "");
        }else{
            result = new RespResult<>(100201,"不支持该类型数据","不支持该类型数据","", config.getEnv(), "");
        }
        return result;
    }
    @PostMapping("/diet")
    public RespResult<?>getDiet(@RequestBody Map<String,String>map,HttpSession session){
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dUID = (String) session.getAttribute("UID");
        String uUID = map.get("uUID");
        String dataType = map.get("dataType");
        String fromStr = map.getOrDefault("from","2020-01-01 00:00:00");
        String toStr = map.getOrDefault("to", LocalDateTime.now().format(fm));
        RespResult<?> result;
        if (doctorUserService.Find(dUID,uUID) == 0){
            result = new RespResult<>(BaseRespResultCode.PERMISSION_DENIED,"", config.getEnv(),"");
            return result;
        }
        if(dataType==null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        LocalDateTime from;
        LocalDateTime to;
        try{
            from = LocalDateTime.parse(fromStr,fm);
            to = LocalDateTime.parse(toStr,fm);
        }catch (DateTimeParseException e){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return  result;
        }
        if (dataType.equals("diet"))
        {
            List<dietRecord> lst = dietRecordService.query(uUID,from,to);
            result = new RespResult<>(BaseRespResultCode.OK,lst, config.getEnv(), "");
        }else{
            result = new RespResult<>(100201,"不支持该类型数据","不支持该类型数据","", config.getEnv(), "");
        }
        return result;
    }
}
