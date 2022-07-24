package com.gx.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.google.gson.JsonObject;
import com.gx.msmservice.service.MsmService;
import com.gx.msmservice.utils.MsmProperties;
import com.gx.servicebase.config.exception.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/22 16:58
 */
@Service
@Slf4j
public class MsmServiceImpl implements MsmService {
    @Autowired
    private MsmProperties msmProperties;

    //发送短信
    @Override
    public boolean sendShortMessage(String phone, Map<String, Object> code) {
        try {
            Config config = new Config()
                    // 您的 AccessKey ID
                    .setAccessKeyId(msmProperties.getAccessKeyId())
                    // 您的 AccessKey Secret
                    .setAccessKeySecret(msmProperties.getAccessKeySecret());
            // 访问的域名
            config.endpoint = msmProperties.getEndpoint();
            Client client = new Client(config);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setSignName(msmProperties.getSignName())
                    .setTemplateCode(msmProperties.getTemplateCode())
                    .setPhoneNumbers(phone)
                    .setTemplateParam(JSONObject.toJSONString(code));
            RuntimeOptions runtime = new RuntimeOptions();
            // 复制代码运行请自行打印 API 的返回值
            SendSmsResponse sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, runtime);
            log.info("验证码发送状态:" + sendSmsResponse.statusCode.toString());
            if (sendSmsResponse.statusCode == 200) {
                return true;
            } else {
                throw new GuliException(20001, "验证码发送失败");
            }
            //发送成功存入redis中
        } catch (Exception e) {
            //打印
            e.printStackTrace();
            return false;
        }
    }
}
