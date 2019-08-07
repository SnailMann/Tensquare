package com.snailmann.tensquare.qa.interceptor;

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
 * JWT认证拦截器
 * (1) 只负责JWT的信息认证，既解析JWT字符串信息，得到用户信息，角色信息
 * (2) 既JWT认证拦截器只负责登录认证，判断有无登录，JWT是否过期，具体对请求的权限拦截交给别的拦截器去做
 * (3) 只有登录认证的判断的做过了，才能进行下一步的权限拦截
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获得JWT请求头
        String header = request.getHeader("Authorization");

        //如果JWT请求头不为空，且以约定规则Bearer+空格开头，则确定是jwt token
        if (!StringUtils.isBlank(header)) {
            if (header.startsWith("Bearer ")) {
                String realToken = header.substring(7);
                try {
                    //解析JWT Token
                    Claims claims = jwtUtil.parseJWT(realToken);
                    String roles = (String) claims.get("roles");
                    if ("user".equals(roles)) {
                        request.setAttribute("claims_user", realToken);
                    }
                    if ("admin".equals(roles)) {
                        request.setAttribute("claims_admin", realToken);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    throw new Exception("登录认证失败！");

                }
            }
        }
        return true;
    }

}
