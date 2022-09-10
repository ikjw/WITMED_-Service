package com.example.test.old.old_dao;

import com.example.test.old.entity.Captcha;
import org.apache.ibatis.annotations.Param;

public interface ICaptchaDao {
    Captcha getblob(@Param("id")int id);
}
