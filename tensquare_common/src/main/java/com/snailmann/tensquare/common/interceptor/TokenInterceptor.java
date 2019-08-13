package com.snailmann.tensquare.common.interceptor;

import com.snailmann.tensquare.common.context.TokenContext;
import com.snailmann.tensquare.common.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
        if (StringUtils.isNoneBlank(token) && token.startsWith("Bearer ")) {
            String realToken = token.substring(7);
            Claims auth = jwtUtil.parseJWT(realToken);
            tokenContext.set(auth);
            log.warn("JWT Token :{}", tokenContext.get());
        }


        return true;
    }
}
