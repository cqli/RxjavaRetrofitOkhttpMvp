package com.example.lcq.exception;

/**
 * Created by lcq on 2016/11/6.
 * 21:58
 * com.example.lcq
 * 自定义的异常,处理解析网络时的错误
 */

public class ApiException extends RuntimeException {

    public int code;
    public String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
