package com.snailmann.tensquare.common.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 可以用于解决Hystrix线程隔离导致的RequestContextHolder无法获取Request的问题
 */
@Slf4j
@Component
public class RequestInheritableContext implements Filter {

    private static ThreadLocal<HttpServletRequest> httpServletRequestHolder =
            new InheritableThreadLocal<HttpServletRequest>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        // 绑定到当前线程
        httpServletRequestHolder.set((HttpServletRequest) request);
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            httpServletRequestHolder.remove(); // 清理资源引用
        }
    }

    @Override
    public void destroy() {
    }

    public static HttpServletRequest getRequest() {
        return httpServletRequestHolder.get();
    }

}