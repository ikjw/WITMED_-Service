package com.example.test.exception;

import com.alibaba.fastjson.JSON;
import com.example.test.config.envConfig;
import com.example.test.utils.HttpHelper;
import com.example.test.utils.Imp.RespResult;
import com.example.test.utils.Imp.BaseRespResultCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    @Autowired
    envConfig config;

    @ExceptionHandler({Exception.class, Throwable.class})
    @ResponseBody
    public RespResult<?> handler(Throwable throwable, HttpServletRequest request){
        RespResult<?> result = new RespResult<>(BaseRespResultCode.SYS_EXCEPTION,"",config.getEnv(),"");
        if(config.getEnv().equals("dev")){
            result.setDetailMessage(throwable.getMessage()+"\n"+ Arrays.toString(throwable.getStackTrace()));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("url",request.getRequestURI());
        map.put("headers",parseRequestHeaders(request));
        map.put("body", JSON.parse(HttpHelper.getBodyString(request)));
        map.put("method",request.getMethod());
        map.put("params",parseParams(request));
        map.put("message",throwable.getMessage());
        map.put("stackTrace",Arrays.toString(throwable.getStackTrace()));
        log.atError().log(JSON.toJSONString(map));
        return result;
    }
    public static Map<?,?> parseParams (HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            map.put(name,request.getParameter(name));
        }
        return map;
    }

    public static Map<?,?> parseRequestHeaders (HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name  = headerNames.nextElement();
            String value = request.getHeader(name);
            map.put(name,value);
        }
        return map;
    }
}
