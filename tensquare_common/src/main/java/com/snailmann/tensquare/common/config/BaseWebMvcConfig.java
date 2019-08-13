package com.snailmann.tensquare.common.config;

import com.snailmann.tensquare.common.interceptor.TokenInterceptor;
import com.snailmann.tensquare.common.interceptor.URLInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author SnailMann
 */
@Configuration
public class BaseWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    URLInterceptor urlInterceptor;
    @Autowired
    TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(urlInterceptor).addPathPatterns("/**");
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
    }
}
