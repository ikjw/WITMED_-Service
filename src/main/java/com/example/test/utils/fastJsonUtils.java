package com.example.test.utils;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
public class fastJsonUtils {

    public static <T> List<T> linkedMapTypeListToObjectList(Object lst, Class<T[]> responseType) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(lst);
        mapper.findAndRegisterModules();
        //T[] pp1 = mapper.readValue(json,responseType);
        return Arrays.asList(mapper.readValue(json, responseType));
    }
    public static <T> T linkedMapToObject(Object obj,Class<T> responseType){
        return JSONObject.parseObject(JSONObject.toJSONString(obj)).toJavaObject(responseType);
    }
    public static String objectToJsonStr(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
    public static <T> List<T>  jsonStrToObjectList(String json,Class<T[]> responseType) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return Arrays.asList(mapper.readValue(json, responseType));
    }
    public static <T> T jsonStrToObject(String json,Class<T> responseType) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper.readValue(json, responseType);
    }
}
