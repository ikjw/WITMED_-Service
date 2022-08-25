package com.example.test.controller;

import com.example.test.configBean.envConfig;
import com.example.test.entity.dishResult;
import com.example.test.utils.RespResult;
import com.example.test.utils.baidu.GsonUtils;
import com.example.test.utils.baidu.HttpUtil;
import com.example.test.utils.enums.BaseRespResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.Map;

/**
 * 100800~100899
 */
@RestController
public class DishAndCalController {
    @Autowired
    envConfig config;
    @PostMapping("/dish")
    public RespResult<?> dish(@RequestBody Map<String,String> map){
        RespResult<?> result;
        Map<String,String> temp = null;
        if(config.getEnv().equals("dev")){
            temp = map;
        }
        if(!map.containsKey("img")){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,temp, config.getEnv(), "");
        }else{
            try{
                result = new RespResult<>(BaseRespResultCode.OK,baiduDish(map.get("img")),config.getEnv(),"");
            }catch (Exception e){
                result = new RespResult<>(100800,"api异常",e.getMessage(),temp, config.getEnv(), "");
            }
        }
        return result;
    }


    public dishResult baiduDish(String imgStr) throws Exception {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/dish";
//            // 本地文件路径
//            String filePath = "D:\\Smartheal\\后端\\harmony-osapp-server\\src\\main\\resources\\static\\烤鱼.jpeg";
//            byte[] imgData = FileUtil.readFileByBytes(filePath);
//            String imgStr = Base64Util.encode(imgData);
        String imgParam = URLEncoder.encode(imgStr, "UTF-8");
        String param = "image=" + imgParam + "&top_num=" + 5;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
        String accessToken = "24.229f1b07f8c16f81b72606ea3302402d.2592000.1662359790.282335-26926820";
        String result = HttpUtil.post(url, accessToken, param);
        return GsonUtils.fromJson(result, dishResult.class);

    }
}
