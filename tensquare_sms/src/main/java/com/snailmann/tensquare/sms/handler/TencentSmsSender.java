package com.snailmann.tensquare.sms.handler;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TencentSmsSender {

    /**
     * https://cloud.tencent.com/document/product/382/13613
     * 下面的4个属性，填自己的属性
     */
    private int appid = 1332131;
    private String appkey = "xxxxxxx";
    private int templateId = 123123;
    private String smsSign = "腾讯云";


    public void sender(String[] mobiles, String[] params) {
        try {
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsSingleSenderResult result = ssender.sendWithParam("86", mobiles[0], templateId, params, smsSign, "", "");
            System.out.println(result);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
    }
}
