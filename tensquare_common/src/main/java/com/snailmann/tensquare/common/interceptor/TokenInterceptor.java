package com.snailmann.tensquare.common.interceptor;

import com.snailmann.tensquare.common.context.TokenContext;
import com.snailmann.tensquare.common.exception.AuthException;
import com.snailmann.tensquare.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * TokenInterceptor的作用是，上游调用下游服务时
 * 下游服务自动从Header中，获取token信息，并解析成Claims，存储起来，以备不时之需
 * 同时也可以成为内部的再次权限认证判断，既搭配zuul的外部请求认证，组合成内外互补的认证
 */
@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    TokenContext<Claims> tokenContext = new TokenContext<>();

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 1. 存储Token
         */
        String token = request.getHeader("Authorization");
        log.info("TokenInterceptor : {}", token);
        if (StringUtils.isNoneBlank(token) && token.startsWith("Bearer ")) {
            String realToken = token.substring(7);
            try {
                Claims auth = jwtUtil.parseJWT(realToken);
                tokenContext.set(auth);
                log.warn("JWT Token :{}", tokenContext.get());
                return true;
            } catch (Exception e) {
                log.error("TokenInterceptor: ", e);

            }
        }
        throw new AuthException("权限不足，请登录！！");
    }


    /**
     * 请求执行完毕后，要清理ThreadLocal中的引用
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        tokenContext.clear();
    }
}
