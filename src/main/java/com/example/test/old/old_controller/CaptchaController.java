package com.example.test.old.old_controller;

import com.example.test.controller.intf.IPermission;
import com.example.test.old.old_service.ICaptchaService;
import com.example.test.old.entity.Captcha;
import com.example.test.old.old_utils.unZipToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping(value = "/captcha")
public class CaptchaController implements IPermission {
    @Autowired
    private ICaptchaService iCaptchaService;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    private Captcha blob(@RequestBody Captcha captcha) {
        Captcha temp;
        temp = iCaptchaService.getblob(captcha.getId());
        if (temp == null) {
            return null;
        } else {
            try {
                long len = temp.getCaptcha().length();
                byte[] data = temp.getCaptcha().getBytes(1, (int) len);
                temp.setResult(unZipToString.unZipToString(data));
                temp.setId(captcha.getId());
                return temp;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return null;
            }
        }
    }
}
