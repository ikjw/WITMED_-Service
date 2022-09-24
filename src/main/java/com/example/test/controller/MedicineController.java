package com.example.test.controller;

import com.example.test.bean.medicine;
import com.example.test.config.envConfig;
import com.example.test.controller.intf.IPermission;
import com.example.test.service.intf.medicineService;
import com.example.test.utils.Imp.BaseRespResultCode;
import com.example.test.utils.Imp.RespResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RequestMapping("api/v2/medicine")
@RestController
public class MedicineController implements IPermission {
    @Resource
    envConfig config;
    @Resource
    medicineService mService;
    /**
     * {
     *    "keyWord":"xxx",
     *    "pageIndex":1,
     *    "pageCount":20
     * }
     * 根据关键词查询药品，查询结果每pageCount项为一页，若查询页数大于总页数，返回0项
     * pageCount缺省默认为20
     * pageIndex缺省默认为1
     * keyWord缺省默认为""
     *
     */
    @PostMapping("/query")
    public RespResult<?> query(HttpSession session, @RequestBody Map<String,String> map){
        RespResult<?> result;
        String keyWord = map.getOrDefault("keyWord","");
        String indexStr = map.getOrDefault("pageIndex","1");
        String pageCountStr = map.getOrDefault("pageCount","20");
        int index=1,count=20;
        try{
            index = Integer.parseInt(indexStr);
            if(index<1) index = 1;
            count = Integer.parseInt(pageCountStr);
            if(count<=0) count = 20;
            List<medicine> lst = mService.query(keyWord,index,count);
            result = new RespResult<>(BaseRespResultCode.OK,lst, config.getEnv(), "");
        }catch (NumberFormatException e){
            result = new RespResult<>(BaseRespResultCode.ERR_PARAM_NOT_LEGAL,"", config.getEnv(),"");
            result.setDetailMessage(e.getMessage());
        }
        return result;
    }
}
