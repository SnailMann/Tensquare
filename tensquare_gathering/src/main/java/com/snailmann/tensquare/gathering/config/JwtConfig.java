package com.snailmann.tensquare.friend.config;

import com.snailmann.tensquare.common.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JWT配置类
 */
@Configuration
public class JwtConfig {

    @Bean
    JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
