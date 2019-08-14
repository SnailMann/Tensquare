package com.snailmann.tensquare.common.exception;

import lombok.Getter;
import lombok.Setter;

public class AuthException extends RuntimeException {

    @Getter
    @Setter
    private String msg;

    public AuthException(String msg) {
        super(msg);
        this.msg = msg;

    }
}
