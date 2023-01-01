package com.example.test.controller;

import com.example.test.bean.recipe;
import com.example.test.service.intf.recipeService;
import com.example.test.utils.ImageToBase64Util;
import com.example.test.utils.Imp.BaseRespResultCode;
import net.sf.json.JSONArray;
import org.springframework.web.bind.annotation.*;
import com.example.test.config.envConfig;
import com.example.test.utils.Imp.RespResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/v2/image")
@RestController
@CrossOrigin
public class ImageController {
    @Resource
    envConfig config;
    @Resource
    recipeService recipeService;
    @PostMapping("/upload")
    public RespResult<?> upload(@RequestBody Map<String,Object> map) throws IOException {
        RespResult<?> result;
        if (map.get("newName")==null||map.get("image")==null) {
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, config.getEnv(), "");
            return result;
        }
        JSONArray jsonArray = JSONArray.fromObject(map.get("image"));
        int success = recipeService.update((String) map.get("newName"),jsonArray,Integer.parseInt(map.get("id").toString()));
        if(success!=0){
            recipe r = recipeService.queryById(Integer.parseInt(map.get("id").toString()));
            Map<String,Object> map1 = new HashMap<>();
            map1.put("id",r.getId());
            map1.put("name",r.getName());
            map1.put("cookMethod",r.getCookMethod());
            map1.put("cookTime",r.getCookTime());
            map1.put("calorie",r.getCalorie());
            map1.put("carbohydrate",r.getCarbohydrate());
            map1.put("protein",r.getProtein());
            map1.put("fat",r.getFat());
            map1.put("cholesterol",r.getCholesterol());
            map1.put("dietaryFiber",r.getDietaryFiber());
            map1.put("minerals",r.getMinerals());
            map1.put("vitamin",r.getVitamin());
            map1.put("others",r.getOthers());
            map1.put("mainMaterials",r.getMainMaterials());
            map1.put("accessories",r.getAccessories());
            map1.put("notCalculated",r.getNotCalculated());
            if (r.getImg()!=null&&!r.getImg().equals(""))
                map1.put("img",JSONArray.fromObject(r.getImg()));
            else map1.put("img",null);
            result = new RespResult<>(BaseRespResultCode.OK,map1, config.getEnv(), "");
        }else{
            result = new RespResult<>(BaseRespResultCode.OK,null,config.getEnv(),"");
        }
        return result;
    }
}
