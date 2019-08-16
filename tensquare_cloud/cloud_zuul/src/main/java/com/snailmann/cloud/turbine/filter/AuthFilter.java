package com.snailmann.cloud.turbine.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.snailmann.tensquare.common.context.TokenContext;
import com.snailmann.tensquare.common.entity.Result;
import com.snailmann.tensquare.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Slf4j
public class AuthFilter extends ZuulFilter {

    @Autowired
    TokenContext<Claims> tokenContext;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    ObjectMapper objectMapper;

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
        return PRE_TYPE;
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
    public Object run()  {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        /**
         * 1. 过滤转发请求，非拦截请求(比如登录，注册请求)
         * 直接放行
         */
        if ("OPTIONS".equals(request.getMethod())) {
            return null;
        }
        if (request.getRequestURI().indexOf("login") > 0) {
            return null;
        }


        String header = request.getHeader("Authorization");

        /**
         * 2. zuul进行登录认证，只有可以解析成claims，就代表token无误，是登录用户
         * (1) 我们这里只做登录认证，没有进行权限鉴别，因为十次方项目的权限鉴别有些简陋
         */
        if (StringUtils.isNoneBlank(header)) {
            if (header.startsWith("Bearer ")) {
                String realToken = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(realToken);
                    tokenContext.set(claims);
                    requestContext.addZuulResponseHeader("Authorization", header);
                    System.out.println("Zuul网关拦截: " + tokenContext.get());
                    return null;
                } catch (Exception e) {
                    log.error("AuthFilter: ", e);
                }
            }
        }
        /**
         * 不放行,返回权限不足的response
         */
        Result result = new Result(false, 403, "权限不足，请登录！");
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(403);
        try {
            requestContext.setResponseBody(objectMapper.writeValueAsString(result));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        requestContext.getResponse().setCharacterEncoding("utf-8");
        requestContext.getResponse().setContentType("application/json;chartset=utf-8");
        return null;
    }
}
