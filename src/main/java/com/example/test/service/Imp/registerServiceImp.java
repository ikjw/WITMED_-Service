package com.example.test.service.Imp;

import com.aliyun.tea.TeaModel;
import com.example.test.bean.account;
import com.example.test.config.registerConfig;
import com.example.test.dao.accountDao;
import com.example.test.service.intf.registerService;
import com.example.test.utils.CheckPreCondition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class registerServiceImp implements registerService {
    public static com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }
    @Resource
    accountDao accountDao;
    @Resource
    registerConfig registerConfig;
    @Override
    public String sendMs(String phone) {
        int p  = (int)((Math.random()*9+1)*1000);
        String code = String.valueOf(p);
        String templateParam = String.format("{\"code\":\"%s\"}",code);
        com.aliyun.dysmsapi20170525.Client client = null;
        try {
            client = registerServiceImp.createClient(registerConfig.getAccessKeyId(), registerConfig.getAccessKeySecret());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        com.aliyun.dysmsapi20170525.models.SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                .setSignName(registerConfig.getSignName())
                .setTemplateCode(registerConfig.getSetTemplateCode())
                .setPhoneNumbers(phone)
                .setTemplateParam(templateParam);
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        com.aliyun.dysmsapi20170525.models.SendSmsResponse resp = null;
        try {
            resp = client.sendSmsWithOptions(sendSmsRequest, runtime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            com.aliyun.teaconsole.Client.log(com.aliyun.teautil.Common.toJSONString(TeaModel.buildMap(resp)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return code;
    }
    @Override
    public int addAccount(account account){
        CheckPreCondition.notNull(account);
        int result = 0;
        if(accountDao.query(account.getUID(),account.getType()) != null){
            return 0;
        }
        else {
            result = accountDao.insert(account);
            return result;
        }
    }
}
