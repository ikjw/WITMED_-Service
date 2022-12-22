package com.example.test.controller;

import com.example.test.bean.dietRecord;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.dietRecordService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.gson.JsonObject;
import net.sf.json.JSONArray;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequestMapping("/api/v2/diet")
@RestController
public class DietController implements IPermission {
    @Resource
    envConfig config;
    @Resource
    dietRecordService dietService;

    @PostMapping("/add")
    public RespResult<?> add(@RequestBody JsonObject params, HttpSession session){
        RespResult<?> result;
        Map<String,Integer> map = new HashMap<>();
        dietRecord diet = new dietRecord();
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try{
            diet.setName(params.get("name").getAsString());
            diet.setAmount(params.get("amount").getAsInt());
            diet.setType(params.get("type").getAsInt());
            diet.setSource(params.get("source").getAsInt());
            diet.setDetail(params.get("detail").getAsString());
            diet.setStartTime(LocalDateTime.parse(params.get("startTime").getAsString(),fm));
            diet.setEndTime(LocalDateTime.parse(params.get("endTime").getAsString(),fm));
            diet.setImg(params.get("img").getAsJsonArray().toString());
        }catch (Exception e){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,null, config.getEnv(),"");
            return result;
        }
        String UID = (String) session.getAttribute("UID");
        int type = (int) session.getAttribute("type");
        if(type !=1 && type != 3){
            result = new RespResult<>(BaseRespResultCode.PERMISSION_DENIED,config.getEnv(),"");
        }else if(diet.getAmount()<1 || diet.getAmount()>5 || diet.getType()<1 || diet.getType()>5 || diet.getStartTime()==null || diet.getEndTime() == null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,config.getEnv(),"");
        }else{
            map.put("total",1);
            map.put("success",dietService.insert(diet,UID));
            result = new RespResult<>(BaseRespResultCode.OK,map,config.getEnv(),"");
        }
        return result;
    }
    @PostMapping("/get")
    public RespResult<?> get(@RequestBody Map<String,String> map, HttpSession session){
        RespResult<?> result = null;
        String UID = (String) session.getAttribute("UID");
        int type = (int) session.getAttribute("type");
        String fromStr = map.get("from");
        String toStr = map.get("to");
        if(type !=1 && type != 3) {
            result = new RespResult<>(BaseRespResultCode.PERMISSION_DENIED, config.getEnv(), "");
        }else if(fromStr ==null || toStr == null){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, config.getEnv(), "");
        }else{
            LocalDateTime from = null,to = null;
            DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            try{
                from = LocalDateTime.parse(fromStr,fm);
                to = LocalDateTime.parse(toStr,fm);
            }catch (Exception e){
                result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL, config.getEnv(), "");
            }
            if(from != null && to != null){
                List<dietRecord> lst = dietService.query(UID,from,to);
                List lst1 = new ArrayList<>();
                for (dietRecord dietRecord : lst) {
                    Map<String,Object> map1 = new HashMap<>();
                    map1.put("id",dietRecord.getId());
                    map1.put("name",dietRecord.getName());
                    map1.put("amount",dietRecord.getAmount());
                    map1.put("startTime",dietRecord.getStartTime());
                    map1.put("endTime",dietRecord.getEndTime());
                    map1.put("detail",dietRecord.getDetail());
                    if (dietRecord.getImg()!=null&&!dietRecord.getImg().equals("")){
                        map1.put("img", JSONArray.fromObject(dietRecord.getImg()));
                    }
                    else map1.put("img",null);
                    map1.put("source",dietRecord.getSource());
                    map1.put("UID",dietRecord.getUID());
                    map1.put("type",dietRecord.getType());
                    lst1.add(map1);
                }
                result = new RespResult<>(BaseRespResultCode.OK,lst1, config.getEnv(), "");
            }
        }
        return result;
    }

}
