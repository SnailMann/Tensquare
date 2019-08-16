package com.snailmann.tensquare.common.context;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * HttpServletRequst的持有容器
 */
@Component
public class RequestContext {


    /**
     * 获得Request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) (RequestContextHolder.getRequestAttributes());
        try {
            return requestAttributes.getRequest();
        } catch (Exception e) {
            return null;
        }

    }

}
