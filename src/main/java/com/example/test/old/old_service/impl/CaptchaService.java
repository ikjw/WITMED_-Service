package com.example.test.old.old_service.impl;

import com.example.test.old.old_dao.ICaptchaDao;
import com.example.test.old.old_service.ICaptchaService;
import com.example.test.old.entity.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaptchaService implements ICaptchaService {
    //@Autowired
    private ICaptchaDao iCaptchaDao;

    @Override
    public Captcha getblob(int id){

        Captcha temp = iCaptchaDao.getblob(id);
        if(temp.getCaptcha()==null){
            System.out.println("123");
        }
        return temp;
    }
}
