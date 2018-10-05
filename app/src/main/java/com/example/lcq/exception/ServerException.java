package com.example.lcq.exception;

/**
 * Created by lcq on 2016/11/6.
 * 22:23
 * com.example.lcq
 * 自定义的服务器异常
 */

public class ServerException extends RuntimeException {
    public int code;
    public String message;

    public ServerException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
