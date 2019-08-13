package com.snailmann.tensquare.common.interceptor;

import com.snailmann.tensquare.common.context.TokenContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class FeignInvokeInterceptor implements RequestInterceptor {

    @Autowired
    HttpServletRequest request;

    /**
     * 每个feign调用，都会从request中获取header，然后塞到RequestTemplate中，传递给下游服务
     * (1) 可以从request中获取
     * (2) 也可以从tokenContext中获取
     *
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (null != request) {
            requestTemplate.header("Authorization", request.getHeader("Authorization"));
        }
    }


}
