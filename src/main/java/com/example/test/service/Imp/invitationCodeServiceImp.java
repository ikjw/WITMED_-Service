package com.example.test.service.Imp;

import com.example.test.bean.invitationCode;
import com.example.test.dao.invitationCodeDao;
import com.example.test.service.intf.invitationCodeService;
import com.example.test.utils.CheckPreCondition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class invitationCodeServiceImp implements invitationCodeService {
    @Resource
    invitationCodeDao invitationCodeDao;
    @Override
    public String createCode(String dUID){
        CheckPreCondition.notNull(dUID);
        DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        Random yzm = new Random();
        String code = "";
        for (int i = 0; i <= 5; i++) {
            int a = yzm.nextInt(3);                             //验证码包括数字、大小写字母组成
            switch(a){                                          //a:    0       1       2
                case 0:                                         //      数字   小写字母   大写字母
                    char s=(char)(yzm.nextInt(26)+65);
                    code = code + s;
                    break;
                case 1:
                    char s1=(char)(yzm.nextInt(26)+97);
                    code = code + s1;
                    break;
                case 2:
                    int s2=yzm.nextInt(10);
                    code = code + s2;
                    break;
            }
        }
        invitationCodeDao.insert(code,dUID,time.format(fm));
        return code;
    }
    @Override
    public int findCode(String uUID,String code){
        CheckPreCondition.notNull(uUID);
        CheckPreCondition.notNull(code);
        return invitationCodeDao.update(code,uUID);
    }
    @Override
    public List<invitationCode> query(int pageIndex,int pageCount){
        if(pageIndex<1) throw new RuntimeException("pageIndex>=1 ！");
        if(pageCount<=0) throw new RuntimeException("pageCount>0 !");
        return invitationCodeDao.query((pageIndex-1)*pageCount,pageCount);
    }
    @Override
    public int deleteCode(){
        List<invitationCode> lst = invitationCodeDao.get();
        LocalDateTime time = LocalDateTime.now();
        int success = 0;
        long hours;
        for (invitationCode invitationCode : lst) {
            hours = ChronoUnit.HOURS.between(invitationCode.getCreateTime(), time);
            if (hours >=24){
                success += invitationCodeDao.delete(invitationCode.getDUID(),invitationCode.getCode());
            }
        }
        return success;
    }
}
