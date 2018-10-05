package com.example.lcq.utils;

import android.util.Log;

import com.example.lcq.BuildConfig;

/**
 * Created by lcq on 2018/10/5.
 * 日志打印
 */

public class LogUtils {
    public static final boolean isDebug = BuildConfig.DEBUG;

    /**
     * 打印一个debug等级的 log
     */
    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d("lcq" + tag, msg);
        }
    }

    /**
     * 打印一个debug等级的 log
     */
    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e("lcq" + tag, msg);
        }
    }

    /**
     * 打印一个debug等级的 log
     */
    public static void e(Class cls, String msg) {
        if (isDebug) {
            Log.e("lcq" + cls.getSimpleName(), msg);
        }
    }
}
