package com.example.lcq;

import android.app.Application;
import android.content.Context;

/**
 * Created by lcq on 2018/10/5.
 * 应用启动,主要用来做一下初始化的操作
 */

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    /**
     * @return 全局的上下文
     */
    public static Context getmContext() {
        return mContext;
    }
}
