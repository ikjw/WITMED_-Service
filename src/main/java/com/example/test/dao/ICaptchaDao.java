package com.example.test.dao;

import com.example.test.entity.Captcha;
import org.apache.ibatis.annotations.Param;

public interface ICaptchaDao {
    Captcha getblob(@Param("id")int id);
}
