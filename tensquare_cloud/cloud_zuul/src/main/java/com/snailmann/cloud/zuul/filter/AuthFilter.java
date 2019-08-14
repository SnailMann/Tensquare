package com.snailmann.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.snailmann.tensquare.common.context.TokenContext;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    TokenContext<Claims> tokenContext;

    /**
     * 过滤类型
     * pre,前置拦截
     * post,后置拦截
     * error, 异常拦截
     * aroud, 环绕拦截
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 当前过滤器的优先级，越小，优先级越高
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }


    /**
     * 当前过滤器是否开启
     * true: 开启
     * false: 关闭
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作，return任意数据，都代表放行
     * setSendZuulResponse(false)代表拦截，不放行
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String header = request.getHeader("Authorization");
        if (StringUtils.isNoneBlank(header)) {
            requestContext.addZuulResponseHeader("Authorization", header);
        }
        System.out.println("Zuul网关拦截: " + tokenContext.get());
        return null;
    }
}
