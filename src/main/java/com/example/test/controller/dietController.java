package com.example.test.controller;

import com.example.test.entity.Information;
import com.example.test.entity.Weight;
import com.example.test.configBean.dietPlanConfig;
import com.example.test.entity.dietplan;
import com.example.test.service.IInformationService;
import com.example.test.service.IWeightService;
import com.example.test.service.IdietplanService;
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

@EnableAutoConfiguration
@RestController
@RequestMapping(value="/diet")
public class dietController {
    @Autowired
    IdietplanService dietService;
    @Autowired
    IInformationService informationService;
    @Autowired
    IWeightService weightService;
    @Autowired
    dietPlanConfig config;
    @RequestMapping("/add")
    private Map<String,Object> add(@RequestBody dietplan plan){
        Map<String,Object> ret = new HashMap<>();
        ret.put("code",dietService.add(plan));
        return ret;//1 成功 0 失败
    }
    @RequestMapping("/getByPatient")
    private List<dietplan> getByPatient(@RequestBody Map<String,Object> map){
        return dietService.queryByPatient((String)map.get("targetUsername"));
    }
    @RequestMapping("/getByPatientAndDate")
    private dietplan getByPatientAndDate(@RequestBody Map<String,Object> map){
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
        return plan;
    }
    @RequestMapping(value="/getRecipe",method = RequestMethod.POST)
    private String getRecipe(@RequestBody dietplan plan){
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
                    String[] temp = value.split("-");
                    min[index] = temp[0];
                }else{
                    min[index] = value;
                }
            }
        }
        try {
            String[] args1 = new String[] {config.getPythonExe(),
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
            return jsonstr;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello";
    }

    @RequestMapping(value = "/getDietPlan",method = RequestMethod.POST)
    private String getDietPlan(@RequestBody Map<String,Object> map){
        Information info  = informationService.get((String)map.get("Username"));
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
            return jsonstr;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello";
    }
}
