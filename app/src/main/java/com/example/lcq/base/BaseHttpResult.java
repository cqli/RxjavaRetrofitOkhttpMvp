package com.example.lcq.base;

/**
 * Created by lcq on 2016/10/21.
 * 抽取的一个基类的bean,直接在泛型中传data就行
 */

public class BaseHttpResult<T> {
    private T datas;
    private int code;
    private String errorMsg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    public T getData() {
        return datas;
    }

    public void setData(T data) {
        this.datas = data;
    }

    @Override
    public String toString() {
        return "BaseHttpResult{" +
                "datas=" + datas +
                ", code=" + code +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
