package com.example.test.service.Imp;

import com.example.test.bean.dietRecord;
import com.example.test.config.envConfig;
import com.example.test.dao.dietRecordDao;
import com.example.test.service.intf.dietRecordService;
import com.example.test.utils.CheckPreCondition;
import com.example.test.utils.ImageToBase64Util;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
@Service
public class dietRecordServiceImp implements dietRecordService {
    @Resource
    envConfig config;
    @Resource
    dietRecordDao dao;
    @Override
    public int insert(dietRecord record, String mUID) {
        CheckPreCondition.notNull(mUID);
        CheckPreCondition.notNull(record);
        record.setUID(mUID);
        List<dietRecord> lst = this.query(mUID,record.getStartTime(),record.getEndTime().plusMinutes(1));
        for(dietRecord i: lst){
            if(i.getType() == record.getType()
                    && i.getAmount() == record.getAmount()
                    && i.getStartTime().isEqual(record.getStartTime())
                    && i.getEndTime().isEqual(record.getEndTime())){
                return 0;
            }
        }
        if(record.getImg()==null || record.getImg().length() == 0){
            record.setImg("[]");
        }else{
            JSONArray arr = JSONArray.fromObject(record.getImg());
            JSONArray fileName = new JSONArray();
            for(Object i : arr){
                File file = ImageToBase64Util.convertBase64ToFile((String)i,config.getImgPath());
                if(file == null){
                    record.setImg("");
                }else{
                    fileName.add(file.getName());
                }
            }
            record.setImg(fileName.toString());
        }
        return  dao.insert(record);
    }

    @Override
    public List<dietRecord> query(String UID, LocalDateTime from, LocalDateTime to) {
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dao.query(UID,from.format(fm),to.format(fm));
    }
}
