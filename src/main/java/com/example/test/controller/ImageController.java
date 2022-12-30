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
        for (int i = 0 ; i < jsonArray.size();i++)
        {
            String s  = jsonArray.getString(i);
            File file = ImageToBase64Util.convertBase64ToFile(s, config.getRecipeImage());
            String fileName = file.getName();
            int success = recipeService.update((String) map.get("newName"), fileName,Integer.parseInt(map.get("id").toString()));
        }
        recipe recipe = recipeService.queryById(Integer.parseInt(map.get("id").toString()));
        recipeService.updateName((String) map.get("NewName"),Integer.parseInt(map.get("id").toString()));
        result = new RespResult<>(BaseRespResultCode.OK,recipe, config.getEnv(), "");
        return result;
    }
}
