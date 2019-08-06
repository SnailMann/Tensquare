package com.snailmann.tensquare.sms.handler;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.snailmann.tensquare.sms.config.ParameterConfig;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TencentSmsSender {

    /**
     * https://cloud.tencent.com/document/product/382/13613
     * 下面的4个属性，填自己的属性
     */
    @Autowired
    ParameterConfig parameterConfig;

    public void sender(String[] mobiles, String[] params) {
        try {
            SmsSingleSender ssender = new SmsSingleSender(parameterConfig.appid, parameterConfig.appkey);
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsSingleSenderResult result = ssender.sendWithParam("86", mobiles[0],
                    parameterConfig.templateId, params, parameterConfig.smsSign, "", "");
            System.out.println(result);
        } catch (HTTPException | JSONException | IOException e) {
            // HTTP响应码错误，json解析错误，网络IO错误
            e.printStackTrace();
        }


    }
}
