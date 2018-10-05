package com.example.lcq.base;


import com.example.lcq.http.Http;
import com.example.lcq.http.HttpService;
import com.example.lcq.mvp.IModel;

/**
 * Created by lcq on 2016/12/1.
 * 23:13
 * com.example.lcq
 */

public class BaseModel implements IModel {
    protected static HttpService httpService;

    //初始化httpService
    static {
        httpService = Http.getHttpService();
    }

}
