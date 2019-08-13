package com.snailmann.tensquare.qa.client.fallback;

import com.snailmann.tensquare.common.entity.Result;
import com.snailmann.tensquare.common.entity.StatusCode;
import com.snailmann.tensquare.qa.client.BaseClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * BaseClientFallBack实现简单，没有无法记录异常信息
 */
@Slf4j
@Component
public class BaseClientFactory implements FallbackFactory<BaseClient> {
    @Override
    public BaseClient create(Throwable throwable) {
        return new BaseClient() {
            @Override
            public Result findById(String id) {
                log.error("hystrix熔断降级：", throwable);
                return new Result(false, StatusCode.ERROR, "熔断器降级到当前操作...");
            }
        };
    }
}
