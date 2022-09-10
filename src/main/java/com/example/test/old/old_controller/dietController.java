package com.example.test.old.old_controller;

import com.example.test.config.envConfig;
import com.example.test.old.old_service.IWeightService;
import com.example.test.controller.intf.IPermission;
import com.example.test.old.entity.Information;
import com.example.test.old.entity.Weight;
import com.example.test.config.dietPlanConfig;
import com.example.test.old.entity.dietplan;
import com.example.test.old.old_service.IInformationService;
import com.example.test.old.old_service.IdietplanService;
import com.example.test.utils.Imp.RespResult;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 100700 - 100799
 */
@EnableAutoConfiguration
@RestController
@RequestMapping(value="/diet")
public class dietController implements IPermission {
    @Autowired
    IdietplanService dietService;
    @Autowired
    IInformationService informationService;
    @Autowired
    IWeightService weightService;
    @Autowired
    dietPlanConfig config;

    @Autowired
    envConfig envConfig;
    @RequestMapping("/add")
    private RespResult<?> add(@RequestBody dietplan plan){
        RespResult<?> result;
        dietplan temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = plan;
        }
        if(plan.getDoctorUsername() == null || plan.getTargetUsername() == null || plan.getStart() == null
            || plan.getEnd() == null || plan.getBean() == null || plan.getFruit() == null || plan.getMainFood() == null
                || plan.getMeatAndEgg()==null || plan.getMilk()==null || plan.getNut() == null || plan.getOil() == null
                || plan.getSalt() == null || plan.getTotalEnergy() ==null || plan.getTotalFood() == null
        ){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp, envConfig.getEnv(), "");
        }else{
            dietService.add(plan);
            result = new RespResult<>(BaseRespResultCode.OK,temp, envConfig.getEnv(), "");
        }
        return result;//1 成功 0 失败
    }
    @RequestMapping("/getByPatient")
    private RespResult<?> getByPatient(@RequestBody Map<String,String> map){
        RespResult<?> result;
        Map<String,String> temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = map;
        }
        if(!map.containsKey("targetUsername")){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp, envConfig.getEnv(), "");
        }else{
            List<dietplan> lst = dietService.queryByPatient(map.get("targetUsername"));
            result = new RespResult<>(BaseRespResultCode.OK,lst,envConfig.getEnv(),"");
        }
        return result;
    }
    @RequestMapping("/getByPatientAndDate")
    private RespResult<?>  getByPatientAndDate(@RequestBody Map<String,String> map){
        RespResult<?> result;
        Map<String,String> temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = map;
        }
        if(!map.containsKey("targetUsername") || !map.containsKey("date")){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp,envConfig.getEnv(),"");
        }else{
            List<dietplan> lst = dietService.queryByPatient((String)map.get("targetUsername"));
            LocalDate date = LocalDate.parse((String)map.get("date"));
            dietplan plan = null;
            for(dietplan i:lst){
                LocalDate start = LocalDate.parse(i.getStart());
                LocalDate end = LocalDate.parse(i.getEnd());
                if((date.isAfter(start) && date.isBefore(end)) || date.equals(start) || date.equals(end)){
                    plan = i;
                }
            }
            result = new RespResult<>(BaseRespResultCode.OK,plan, envConfig.getEnv(), "");
        }

        return result;
    }
    @RequestMapping(value="/getRecipe",method = RequestMethod.POST)
    private RespResult<?> getRecipe(@RequestBody dietplan plan){
        RespResult<?> result;
        dietplan temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = plan;
        }
        String[] itemNames= {"mainFood","vegetables","fruit","meatAndEgg","bean","milk","nut","oil","salt"};
        String[] min = new String[itemNames.length];
        List<String> indexes = Arrays.asList(itemNames);
        Class c1 = plan.getClass();
        Field[] fields = c1.getDeclaredFields();
        for(Field i : fields){
            i.setAccessible(true);
            if(indexes.contains(i.getName())){
                String key = i.getName();
                int index = indexes.indexOf(key);
                String value = "";
                try {
                    value = (String)i.get(plan);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }
                if(value.matches("^\\d*-\\d*$")){
                    String[] temp1 = value.split("-");
                    min[index] = temp1[0];
                }else{
                    min[index] = value;
                }
            }
        }
        try {
            String[] args1 = new String[] {
                    config.getPythonExe(),
                    config.getRecipeScript(),
                   min[0],min[1],min[2],min[3],min[4],min[5],min[6],min[7],min[8]};
            Process proc = Runtime.getRuntime().exec(args1);// 执行py文件
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            StringBuilder builder =new StringBuilder();
            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
            in.close();
            proc.waitFor();
            String jsonstr = builder.toString();
            ObjectMapper objectMapper = new ObjectMapper();
            LinkedHashMap  msgMap = objectMapper.readValue(jsonstr, LinkedHashMap.class);
            result = new RespResult<>(BaseRespResultCode.OK,msgMap, envConfig.getEnv(),"");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            result = new RespResult<>(100700,"系统异常",e.getMessage(),temp,envConfig.getEnv(),"");
        }
        return result;
    }

    @RequestMapping(value = "/getDietPlan",method = RequestMethod.POST)
    private RespResult<?> getDietPlan(@RequestBody Map<String,String> map){
        RespResult<?> result;
        Map<String,String> temp = null;
        if(envConfig.getEnv().equals("dev")){
            temp = map;
        }
        if(!map.containsKey("username")){
            result = new RespResult<>(BaseRespResultCode.OK,temp, envConfig.getEnv(), "");
        }else{
            Information info  = informationService.get(map.get("username"));
            Weight weight = weightService.getRecentWeight(info.getUsername());
            double beforeBMI = info.getWeight1()/(info.getHeight1()/100)/(info.getHeight1()/100);
            double nowBMI = weight.getWeight()/(info.getHeight1()/100)/(info.getHeight1()/100);
            int week = (int) ChronoUnit.WEEKS.between(LocalDate.parse(info.getDate(), DateTimeFormatter.ofPattern("yyyy-M-d")),LocalDate.now());
            int age = LocalDate.now().getYear()-info.getBirth()+1;
            int speed = 1;
            int bmiIndex = 0;
            double rate = (weight.getWeight()-info.getFfm())/weight.getWeight();
            if(beforeBMI<18)
                bmiIndex = 1;
            else if(beforeBMI>=18 && beforeBMI<24){
                bmiIndex = 2;
            }else{
                bmiIndex = 3;
            }
            double[] num = {week,age,info.getHeight1()/100.0,info.getWeight1(),weight.getWeight(),speed,info.getUcre(),
                    beforeBMI,bmiIndex,rate,info.getFfm(),info.getMuscle(),info.getProtein()};
            try {
                String[] args1 = new String[] {config.getPythonExe(),
                        config.getDietScript(),
                        String.valueOf(num[0]),
                        String.valueOf(num[1]),
                        String.valueOf(num[2]),
                        String.valueOf(num[3]),
                        String.valueOf(num[4]),
                        String.valueOf(num[5]),
                        String.valueOf(num[6]),
                        String.valueOf(num[7]),
                        String.valueOf(num[8]),
                        String.valueOf(num[9]),
                        String.valueOf(num[10]),
                        String.valueOf(num[11]),
                        String.valueOf(num[12])};
                Process proc = Runtime.getRuntime().exec(args1);// 执行py文件
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                String line = null;
                StringBuilder builder =new StringBuilder();
                while ((line = in.readLine()) != null) {
                    builder.append(line);
                }
                in.close();
                proc.waitFor();
                String jsonstr = builder.toString();
                ObjectMapper objectMapper = new ObjectMapper();
                LinkedHashMap  msgMap = objectMapper.readValue(jsonstr, LinkedHashMap.class);
                result = new RespResult<>(BaseRespResultCode.OK,msgMap, envConfig.getEnv(),"");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                result = new RespResult<>(100700,"系统异常",e.getMessage(),temp,envConfig.getEnv(),"");
            }
        }
        return result;
    }
}
