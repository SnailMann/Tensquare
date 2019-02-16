package com.snailmann.tensquare.article.config;

import com.snailmann.tensquare.common.util.IdWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式ID生成器的config
 * 作用就是为了让该工具类注册到Spring容器中
 * @author SnailMann
 */
@Configuration
public class IdWokerConfig {

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }

}
