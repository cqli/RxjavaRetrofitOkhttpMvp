package com.example.lcq.subscriber;

import android.content.Context;

import com.example.lcq.base.BaseSubscriber;
import com.example.lcq.exception.ApiException;
import com.example.lcq.utils.LogUtils;
import com.example.lcq.utils.NetworkUtil;


/**
 * Created by lcq on 2016/11/6.
 * 22:42
 * com.example.lcq
 */

public abstract class CommonSubscriber<T> extends BaseSubscriber<T> {

    private Context context;

    public CommonSubscriber(Context context) {
        this.context = context;
    }

    private static final String TAG = "CommonSubscriber";

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onStart() {

        if (!NetworkUtil.isNetworkAvailable(context)) {
            LogUtils.e(TAG, "网络不可用");
        } else {
            LogUtils.e(TAG, "网络可用");
        }
    }



    @Override
    protected void onError(ApiException e) {
        LogUtils.e(TAG, "错误信息为 " + "code:" + e.code + "   message:" + e.message);
    }

    @Override
    public void onCompleted() {
        LogUtils.e(TAG, "成功了");
    }

}
