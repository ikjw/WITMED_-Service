package com.example.test.controller;

import com.example.test.bean.bloodSugar;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.bloodSugarService;
import com.example.test.utils.CheckPreCondition;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import com.example.test.utils.fastJsonUtils;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestController
@RequestMapping("/api/v2/data")
public class DataController implements IPermission {
    @Override
    public boolean hasPermission(String username, int role, String URI) {
        return role == 1 || role == 3;
    }
    @Resource
    envConfig config;
    @Resource
    bloodSugarService sugarService;
    /**
     * ResponseBody:
     * {
     *     "dataType":"bloodSugar",//不能为空
     *     "from":"yyyy-MM-dd HH:mm:ss",
     *     "to":"yyyy-MM-dd HH:mm:ss"
     * }
     * pre-condition:
     * ResponseBody中dataType不能为空
     * session中，UID和type（用户类型）不能为空
     * 访问权限：
     * session中的type为1或3时可以访问，其余不可以
     * post-condition:
     * dataType可以取"bloodSugar","weight"...
     * 当dataType为"bloodSugar"时，
     * 查询 [from,to) 的所有血糖数据
     * from为空则 from为 2020-01-01 00:00:00
     * to为空则 to 为 当前时间
     * 当dataType为“weight”时，
     * 与“bloodSugar”类似，不过from和to格式只取 yyyy-MM-dd
     * @param map ResponseBody
     * @param session session
     * @return 根据处理结果返回的标准响应体
     */
    @RequestMapping("/get")
    public RespResult<?> get(@RequestBody Map<String,String> map, HttpSession session){
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String UID = (String)session.getAttribute("UID");
        String dataType = map.get("dataType");
        String fromStr = map.getOrDefault("from","2020-01-01 00:00:00");
        String toStr = map.getOrDefault("to",LocalDateTime.now().format(fm));
        RespResult<?> result;
        if(dataType==null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        LocalDateTime from;
        LocalDateTime to;
        try{
            from = LocalDateTime.parse(fromStr,fm);
            to = LocalDateTime.parse(toStr,fm);
        }catch (DateTimeParseException e){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return  result;
        }
        if(dataType.equals("bloodSugar")){
            List<bloodSugar> lst = sugarService.query(UID,from,to);
            result = new RespResult<>(BaseRespResultCode.OK,lst, config.getEnv(), "");
        }else if(dataType.equals("weight")){
            //这一行写的时候删了
            result = new RespResult<>(100201,"不支持该类型数据","不支持该类型数据","", config.getEnv(), "");
            //todo
        }else{
            result = new RespResult<>(100201,"不支持该类型数据","不支持该类型数据","", config.getEnv(), "");
        }
        return result;
    }

    /**
     * ResponseBody:
     * {
     *     "dataType":"bloodSugar",//不能为空
     *     "data":[]
     * }
     * pre-condition:
     * ResponseBody中dataType不能为空
     * session中，UID和type（用户类型）不能为空
     * 访问权限:
     * session中的type为1或3时可以访问，其余不可以
     * post-condition:
     * 将data中满足约束的数据记录插入数据库中
     * @param map ResponseBody
     * @param session session
     * @return 根据处理结果返回的标准响应体
     */
    @PostMapping("/add")
    public RespResult<?> add(@RequestBody Map<String,Object> map,HttpSession session){
        //todo
        String UID = (String)session.getAttribute("UID");
        String dataType = (String) map.get("dataType");
        Object data = map.get("data");
        RespResult<?> result;
        if(dataType==null || data == null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            return result;
        }
        int success = 0;
        int total = 0;
        if(dataType.equals("bloodSugar")){
            try{
                List<bloodSugar> lst;
                lst = fastJsonUtils.linkedMapTypeListToObjectList(data, bloodSugar[].class);
                total = lst.size();
                success = sugarService.batchInsert(lst,UID);
            }catch (Exception e){
                result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
                result.setDetailMessage(e.getMessage());
                return  result;
            }
        }else if(dataType.equals("weight")){
            //todo
        }else{
            result = new RespResult<>(100201,"不支持该类型数据","不支持该类型数据","", config.getEnv(), "");
            return  result;
        }
        Map<String,Object> resultInfo = new HashMap<>();
        resultInfo.put("success",success);
        resultInfo.put("total",total);
        result = new RespResult<>(BaseRespResultCode.OK,resultInfo, config.getEnv(), "");
        return result;
    }


}