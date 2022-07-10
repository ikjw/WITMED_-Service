package com.example.test.controller;

import com.example.test.entity.dietPlanConfig;
import com.example.test.entity.dietplan;
import com.example.test.service.IdietplanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;

@EnableAutoConfiguration
@RestController
@RequestMapping(value="/diet")
public class dietController {
    @Autowired
    IdietplanService dietService;
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
        double[] num = {12,30,1.62,64,65.27,1,1.46732,24.39,3,0.3107,44.99,42.53,9.36};
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
