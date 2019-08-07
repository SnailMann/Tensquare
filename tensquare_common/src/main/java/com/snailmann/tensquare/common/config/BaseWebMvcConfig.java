package com.snailmann.tensquare.common.config;

import com.snailmann.tensquare.common.interceptor.URLInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author SnailMann
 */
@Configuration
public class BaseWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new URLInterceptor()).addPathPatterns("/**");
    }
}
