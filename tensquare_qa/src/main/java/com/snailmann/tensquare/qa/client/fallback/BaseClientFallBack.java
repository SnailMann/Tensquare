package com.snailmann.tensquare.qa.client.fallback;

import com.snailmann.tensquare.common.entity.Result;
import com.snailmann.tensquare.common.entity.StatusCode;
import com.snailmann.tensquare.qa.client.BaseClient;
import org.springframework.stereotype.Component;

@Component
public class BaseClientFallBack implements BaseClient {
    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.ERROR, "熔断器降级到当前操作...");
    }
}
