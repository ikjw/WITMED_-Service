package com.example.test.controller;

import com.example.test.service.intf.recipeService;
import com.example.test.utils.Imp.BaseRespResultCode;
import org.springframework.web.bind.annotation.*;
import com.example.test.config.envConfig;
import com.example.test.utils.ImageToBase64Util;
import com.example.test.utils.Imp.RespResult;
import javax.annotation.Resource;
import java.io.*;
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
    public RespResult<?> upload(@RequestBody Map<String,String> map) throws IOException {
        RespResult<?> result;
        if (map.get("name")==null||map.get("image")==null)
        {
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,config.getEnv(),"");
            return result;
        }
        File file = ImageToBase64Util.convertBase64ToFile(map.get("image"), config.getRecipeImage());
        String fileName = file.getName();
        long length = file.length();
        if (length >5000000)
        {
            result = new RespResult<>(102001,"文件过大","文件过大","", config.getEnv(), "");
            return result;
        }
        int success = recipeService.update(map.get("name"), fileName);
        result = new RespResult<>(BaseRespResultCode.OK,success, config.getEnv(), "");
        return result;
    }
}
