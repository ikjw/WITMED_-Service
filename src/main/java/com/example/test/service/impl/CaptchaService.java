package com.example.test.service.impl;

import com.example.test.dao.ICaptchaDao;
import com.example.test.entity.Captcha;
import com.example.test.service.ICaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaptchaService implements ICaptchaService {
    @Autowired
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
