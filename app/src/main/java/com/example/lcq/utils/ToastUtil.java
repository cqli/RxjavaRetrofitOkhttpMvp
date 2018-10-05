package com.example.lcq.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.lcq.BuildConfig;


/**
 * toast 展示工具类
 * 
 * 
 */
public class ToastUtil {

	private static Toast mToast = null;

	/**
	 * 显示toast 确保全局只存在一个toast对象
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void showToast(Context context, String text, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, duration);
		} else {
			mToast.setText(text);
			mToast.setDuration(duration);
		}

		mToast.show();
	}

	public static void showToast(Context context, String text) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}

		mToast.show();
	}

	/**
	 * 
	 * @param context
	 * @param text
	 * @param duration
	 */
	public static void showToastInDebug(Context context, String text,
			int duration) {

		if (BuildConfig.DEBUG) {
			if (mToast == null) {
				mToast = Toast.makeText(context, text, duration);
			} else {
				mToast.setText(text);
				mToast.setDuration(duration);
			}

			mToast.show();
		}

	}

	/**
	 * 
	 * @param context
	 * @param text
	 */
	public static void showToastInDebug(Context context, String text) {
		if (BuildConfig.DEBUG) {
			if (mToast == null) {
				mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
			} else {
				mToast.setText(text);
				mToast.setDuration(Toast.LENGTH_SHORT);
			}
			mToast.show();
		}
	}

}
