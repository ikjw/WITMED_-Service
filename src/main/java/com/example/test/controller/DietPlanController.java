package com.example.test.controller;

import com.example.test.bean.dietPlan;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.dietPlanService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/dietPlan/")
public class DietPlanController implements IPermission {
    @Resource
    envConfig config;
    @Resource
    dietPlanService dietPlanService;
    @Override
    public boolean hasPermission(String username, int role, String URI) {
        return role == 1||role == 2 || role == 3;
    }
    @PostMapping("/getPlan")
    public RespResult<?> getPlan(HttpSession session){
        RespResult<?> result;
        String UID = (String) session.getAttribute("UID");
        dietPlan dietPlan = dietPlanService.queryRecent(UID);
        result = new RespResult<>(BaseRespResultCode.OK,dietPlan, config.getEnv(), "");
        return result;
    }
    @PostMapping("/getPlans")
    public RespResult<?> getPlans(@RequestBody Map<String,String>map, HttpSession session){
        String UID = (String) session.getAttribute("UID");
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fromStr = map.getOrDefault("from","2020-01-01");
        String toStr = map.getOrDefault("to", LocalDate.now().plusDays(1).format(fm));
        RespResult<?> result;
        LocalDate from;
        LocalDate to;
        try{
            from = LocalDate.parse(fromStr,fm);
            to = LocalDate.parse(toStr,fm);
        }catch (DateTimeParseException e){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return  result;
        }
        List<dietPlan> lst = dietPlanService.queryPlans(UID,from,to);
        result = new RespResult<>(BaseRespResultCode.OK,lst, config.getEnv(), "");
        return result;
    }
    @PostMapping("/upload")
    public RespResult<?> upload(@RequestBody Map<String,Object>map,HttpSession session){
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dUID = (String) session.getAttribute("UID");
        String uUID = (String) map.get("uUID");
        RespResult<?> result;
        String startStr = (String) map.get("startDate");
        String endStr = (String) map.get("endDate");
        LocalDateTime time= LocalDateTime.now();
        float mainFood = Float.parseFloat(map.get("mainFood").toString());
        float vegetables = Float.parseFloat(map.get("vegetables").toString());
        float fruits = Float.parseFloat(map.get("fruits").toString());
        float meat_egg = Float.parseFloat(map.get("meat_egg").toString());
        float soybeans =Float.parseFloat(map.get("soybeans").toString());
        float dairy = Float.parseFloat(map.get("dairy").toString());
        float nuts = Float.parseFloat(map.get("nuts").toString());
        float oils = Float.parseFloat(map.get("oils").toString());
        String details = (String) map.get("details");
        int machineId = Integer.parseInt(map.get("machineId").toString());
        LocalDate startDate;
        LocalDate endDate;
        try{
            startDate = LocalDate.parse(startStr,fm);
            endDate = LocalDate.parse(endStr,fm);
        }catch (DateTimeParseException e){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return  result;
        }
        dietPlan dietPlan = new dietPlan();
        dietPlan.setDUID(dUID);
        dietPlan.setUUID(uUID);
        dietPlan.setStartDate(startDate);
        dietPlan.setEndDate(endDate);
        dietPlan.setTime(time);
        dietPlan.setMainFood(mainFood);
        dietPlan.setVegetables(vegetables);
        dietPlan.setFruits(fruits);
        dietPlan.setMeat_egg(meat_egg);
        dietPlan.setSoybeans(soybeans);
        dietPlan.setDairy(dairy);
        dietPlan.setNuts(nuts);
        dietPlan.setOils(oils);
        dietPlan.setDetails(details);
        dietPlan.setMachineId(machineId);
        int success = dietPlanService.insert(dietPlan);
        result = new RespResult<>(BaseRespResultCode.OK,success, config.getEnv(), "");
        return result;
    }
    @PostMapping("/get")
    public RespResult<?> get(@RequestBody Map<String,String>map,HttpSession session){
        String uUID = map.get("uUID");
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fromStr = map.getOrDefault("from","2020-01-01");
        String toStr = map.getOrDefault("to", LocalDate.now().plusDays(1).format(fm));
        RespResult<?> result;
        LocalDate from;
        LocalDate to;
        try{
            from = LocalDate.parse(fromStr,fm);
            to = LocalDate.parse(toStr,fm);
        }catch (DateTimeParseException e){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return  result;
        }
        List<dietPlan> lst = dietPlanService.queryPlans(uUID,from,to);
        result = new RespResult<>(BaseRespResultCode.OK,lst, config.getEnv(), "");
        return result;
    }
}
