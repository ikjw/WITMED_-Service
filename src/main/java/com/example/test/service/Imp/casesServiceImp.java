package com.example.test.service.Imp;
import com.example.test.bean.cases;
import com.example.test.config.envConfig;
import com.example.test.dao.casesDao;
import com.example.test.service.intf.casesService;
import com.example.test.utils.CheckPreCondition;
import com.example.test.utils.ImageToBase64Util;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class casesServiceImp implements casesService {
    @Resource
    envConfig config;
    @Resource
    casesDao casesDao;
    @Override
    public int insert(String UID, String base64, LocalDateTime time){
        CheckPreCondition.notNull(UID);
        CheckPreCondition.notNull(base64);
        CheckPreCondition.notNull(time);
        File file = ImageToBase64Util.convertBase64ToFile(base64,config.getCasesImage());
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(file.getName());
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return casesDao.insert(UID, time.format(fm),jsonArray.toString());
    }
    @Override
    public List<cases> query(String UID){
        CheckPreCondition.notNull(UID);
        return casesDao.query(UID);
    }
}
