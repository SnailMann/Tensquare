package com.snailmann.tensquare.qa.client;

import com.snailmann.tensquare.common.entity.Result;
import com.snailmann.tensquare.qa.client.fallback.BaseClientFactory;
import com.snailmann.tensquare.qa.client.fallback.BaseClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(value = "tensquare-base", fallbackFactory = BaseClientFactory.class)
public interface BaseClient {

    @RequestMapping(value = "/label/{labelId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String id);

}
