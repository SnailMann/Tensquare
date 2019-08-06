package com.snailmann.tensquare.sms.linstener;


import com.snailmann.tensquare.sms.handler.TencentSmsSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@RabbitListener(queues = "sms")
@Component
public class SmsListener {

    @Autowired
    TencentSmsSender tencentSmsSender;

    @RabbitHandler
    public void receiver(Map<String, String> map) {
        try {
            System.out.println("手机号:" + map.get("mobile"));
            System.out.println("验证码:" + map.get("checkcode"));
            System.out.println("过期时间:" + map.get("ttl"));
            //腾讯云模板：您好，您的验证码是：{1} 请谨慎保管！有效期是{2}分钟
            Integer ttl = Integer.valueOf(map.getOrDefault("ttl", "60"));
            int minute = ttl / 60;
            tencentSmsSender.sender(new String[]{map.get("mobile")}, new String[]{map.get("checkcode"), String.valueOf(minute)});
        } catch (Exception e) {
            log.error("消费失败", e);
        }

    }

}
