package com.example.test.controller;

import com.example.test.utils.Imp.BaseRespResultCode;
import org.springframework.web.multipart.MultipartFile;
import com.example.test.config.envConfig;
import com.example.test.utils.ImageToBase64Util;
import com.example.test.utils.Imp.RespResult;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping("api/v2/image")
@RestController
public class ImageController {
    @Resource
    envConfig config;
    @PostMapping("/upLode")
    public RespResult<?> upLode(@RequestBody String image) throws IOException {
        RespResult<?> result;
        String path = "D:/img";
        File filePath = new File(path);
        if (!filePath.exists() && !filePath.isDirectory())
            filePath.mkdir();
        File pfile = ImageToBase64Util.convertBase64ToFile(image, "D:\\images\\");
        InputStream inputStream = new FileInputStream(pfile);
        MultipartFile file = new MockMultipartFile(pfile.getName(), inputStream);
        String fileName = file.getName();
        File targetFile = new File(path, fileName);
        try {
            file.transferTo(targetFile);
            //将文件在服务器的存储路径返回
            result = new RespResult<>(BaseRespResultCode.OK,fileName, config.getEnv(), "");

        } catch (IOException e) {
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
        }
        return result;
    }
}
