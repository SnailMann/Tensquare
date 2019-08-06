package com.snailmann.tensquare.sms.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ParameterConfig {

    @Value("${tencent.sms.appid}")
    public int appid;
    @Value("${tencent.sms.appkey}")
    public String appkey;
    @Value("${tencent.sms.templateid}")
    public int templateId;
    @Value("${tencent.sms.smssign}")
    public String smsSign;


}
