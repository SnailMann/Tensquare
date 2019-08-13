package com.snailmann.tensquare.common.interceptor;

import com.snailmann.tensquare.common.context.TokenContext;
import com.snailmann.tensquare.common.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class URLInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * 1. 记录URL
         */
        String ip = IpUtil.getRequestIp(request);
        String url = request.getRequestURI();
        log.info("Run: {} : {}", ip, url);


        return true;
    }
}
