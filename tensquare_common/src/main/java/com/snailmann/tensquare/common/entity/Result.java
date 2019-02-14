package com.snailmann.tensquare.common.entity;



import lombok.Data;

/**
 * restful通用返回对象
 * @author SnailMann
 */
@Data
public class Result {
    /**
     * 是否成功
     */
    private boolean flag;
    /**
     * 返回状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    public Result() {
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }


}
