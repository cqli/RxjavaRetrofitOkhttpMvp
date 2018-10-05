package com.example.lcq.model;

import android.support.annotation.NonNull;

import com.example.lcq.MyApplication;
import com.example.lcq.base.BaseModel;
import com.example.lcq.bean.ZhongshuohaoCates;
import com.example.lcq.exception.ApiException;
import com.example.lcq.subscriber.CommonSubscriber;
import com.example.lcq.trasformer.CommonTransformer;

import java.util.List;

/**
 * Created by lcq on 2016/11/26.
 * 20:53
 *
 * com.example.lcq
 * 主要做一些数据处理呀,网路请求呀
 */

public class MainModel extends BaseModel {
    private boolean isLogin = false;


    public boolean query( @NonNull final InfoHint
            infoHint) {

        if (infoHint == null)
            throw new RuntimeException("InfoHint不能为空");

        httpService.query("")
                .compose(new CommonTransformer<List<ZhongshuohaoCates>>())
                .subscribe(new CommonSubscriber<List<ZhongshuohaoCates>>(MyApplication.getmContext()) {
                    @Override
                    public void onNext(List<ZhongshuohaoCates> loginBean) {
                        isLogin = true;
                        infoHint.successInfo(loginBean.size()+"");
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        isLogin = false;
                        infoHint.failInfo(e.message);
                    }
                });
        return isLogin;
    }
    //通过接口产生信息回调
    public interface InfoHint {
        void successInfo(String str);

        void failInfo(String str);

    }

}
