package com.example.lcq.http;


import com.example.lcq.base.BaseHttpResult;
import com.example.lcq.bean.ZhongshuohaoCates;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by lcq on 2016/9/13.
 * 网络请求的接口都在这里
 */

public interface HttpService {
    /**
     * 测试接口
     *
     * @param username
     * @return
     */
    @FormUrlEncoded
    @POST("phone/ugm4_ugcCates.xhtml")
    Observable<BaseHttpResult<List<ZhongshuohaoCates>>> query(@Field("userName") String username);

}
