package com.snailmann.tensquare.common.exception;

import com.snailmann.tensquare.common.entity.Result;
import com.snailmann.tensquare.common.entity.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@Slf4j
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        log.error("Exception", e);
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }


    @ExceptionHandler(value = AuthException.class)
    @ResponseBody
    public Result error(AuthException e) {
        log.error("AuthException", e);
        return new Result(false, StatusCode.ACCESS_ERROR, e.getMessage());
    }

}
