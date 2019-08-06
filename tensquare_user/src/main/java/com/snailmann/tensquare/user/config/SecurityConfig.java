package com.snailmann.tensquare.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Security 配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 用于对密码进行BCrypt算法加密
     *
     * @return
     */
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * authorizeRequests是Security权限认证注解配置的开端，表明开启权限及说明执行什么路径所需的权限
         * 权限认证分为两个部分：
         * (1) 第一部分是，要拦截的路径
         * (2) 第二部分是，执行拦截路径所需要的是什么权限
         * 我们这里仅仅是想用Spring Security对密码进行加密，而不是使用Security进行认证和鉴权，所以前提就是要先让
         * Security对所有的路径保持开发，并且关闭csrf功能，允许跨域
         */
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll() //所有路径，全允许通过，不需要权限
                .anyRequest().authenticated() //代表任何请求，都需要认证后，才能访问，既需要登录认证
                .and().csrf().disable();
    }
}
