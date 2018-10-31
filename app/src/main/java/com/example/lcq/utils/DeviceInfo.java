package com.example.lcq.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.EditText;

import com.example.lcq.MyApplication;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by lcq on 2018/10/5.
 * 获取设备信息
 */

public class DeviceInfo {
    private final static String TAG = "DeviceInfo";
    // Context对象
    private Context mContext;
    // TelephonyManager对象
    private TelephonyManager mTelephonyManager;

    private InputMethodManager imm;

    private String token = "";
    private String memcard = "";
    // DeviceInfo的singleton实例
    private static DeviceInfo sDeviceInfo;
    public static String pay;

    /**
     * 获取该类的唯一实例
     *
     * @return 该类的唯一实例
     */
    public static DeviceInfo getInstance() {
        if (sDeviceInfo == null) {
            sDeviceInfo = new DeviceInfo();
        }
        return sDeviceInfo;
    }

    /**
     * 释放该类的唯一实例
     */
    public static void releaseInstance() {
        sDeviceInfo.release();
        sDeviceInfo = null;
    }

    /**
     * 私有构造函数，为实现singleton类
     *
     */
    private DeviceInfo() {
        mContext = MyApplication.getmContext();
        mTelephonyManager = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * 释放context对象
     */
    private void release() {
        mContext = null;
    }

    /**
     * 检测网络是否打开
     *
     * @return 网络是否打开
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        if (network != null) {
            return network.isAvailable();
        }
        return false;
    }


    /**
     * 返回接入网络类型
     *
     * @return
     */
    public int getNetType() {
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        if (network == null) {
            return -1;
        }
        return network.getType();
    }

	/*
     * info.getState(); // 连线状态 [CONNECTED] info.isAvailable(); // 网络是否可用 [true]
	 * info.isConnected(); // 网络是否已经连接 [true] info.isConnectedOrConnecting(); //
	 * 网络是否已经连接或者连接中 [true] info.isFailover(); // 网络是否有问题 [false]
	 * info.isRoaming(); // 网络是否在漫游中 [false] 需要加的权限： <uses-permission
	 * android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
	 */

    /**
     * 返回接入网络类型名称
     *
     * @return
     */
    public String getNetTypeName() {
        // 5种联网类型 cmwap/cmnet/wifi/uniwap/uninet
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        if (network == null) {
            return "-1";
        }
        return network.getTypeName();
    }

    /**
     * 获取本机的IMEI
     *
     * @return IMEI
     */
    public String getIMEI() {
        String myIMEI = mTelephonyManager.getDeviceId();
        return myIMEI;
    }

    /**
     * 获取本机的IMSI，若无，提供一默认的
     *
     * @return IMSI
     */
    public String getIMSI() {
        String myIMSI = mTelephonyManager.getSubscriberId();
        if (myIMSI == null) {
            myIMSI = "310260000000000";
        }
        Log.d("DeviceInfo", "imsi:" + myIMSI);
        return myIMSI;
    }

    public void closeInput(EditText et) {
        try {
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
            et.clearFocus();
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断SD卡是否存在
     *
     * @return
     */
    public boolean sdCardExist() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡的根目录
     *
     * @return
     */
    public String getSDCardPath() {
        return android.os.Environment.getExternalStorageDirectory().toString();
    }

    public String getSDCardFilePath() {
        return mContext.getExternalFilesDir(null).toString();
    }

    /**
     * 获取软件根目录
     *
     * @return
     */
    public String getSelfPath() {
        return mContext.getFilesDir().getAbsolutePath();
    }

    /**
     * 获取分辨率宽
     *
     * @return
     */
    public int getWidth() {
        WindowManager mWm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = mWm.getDefaultDisplay();
        int width = display.getWidth();
        return width;
    }

    /**
     * 获取分辨率高
     *
     * @return
     */
    public int getHeight() {
        WindowManager mWm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = mWm.getDefaultDisplay();
        int height = display.getHeight();
        return height;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        Log.i("DeviceInfo", "fontScale=******************==" + fontScale);
        Log.i("DeviceInfo", "fontScale=******************=="
                + (spValue * fontScale + 0.5f));
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int strLength(String str) {
        int lenght = 0;
        try {
            int temp = str.getBytes("gb2312").length;
            lenght = temp / 2 + (temp % 2 == 1 ? 1 : 0);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return lenght;
    }


    // 根据亮度值修改当前window亮度
    // WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
    public static void changeAppBrightness(Context context, int brightness) {
        Window window = ((Activity) context).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (brightness == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
        }
        window.setAttributes(lp);
    }

    /**
     * 获取分辨率宽*高
     *
     * @return
     */
    public String getDisplay() {
        int width = getWidth();
        int height = getHeight();
        if (width >= height) {
            return width + "*" + height;
        } else {
            return height + "*" + width;
        }
    }
    /**
     * 获取设备型号
     *
     * @return
     */
    public String getDeviceType() {
        return android.os.Build.MODEL;
    }

    public String getSDK() {
        return android.os.Build.VERSION.SDK;
    }

    /**
     * 获取渠道号
     */
    public String getChannel() {
        String channel = "zgjkj_8000001";
//        try {
//            ApplicationInfo appInfo = mContext.getPackageManager()
//                    .getApplicationInfo(mContext.getPackageName(),
//                            PackageManager.GET_META_DATA);
//            channel = appInfo.metaData.getString("UMENG_CHANNEL");
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//        }
        return channel;
    }

    /**
     * 获取操作系统版本号
     *
     * @return
     */
    public String getOsVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取软件的版本号
     *
     * @return 1001
     */
    public int getVersionCode() {
        int versionCode = 0;
        try {
            versionCode = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.toString());
        }
        return versionCode;
    }

    /**
     * 获取软件的版本名称
     *
     * @return 1.0.01
     */
    public String getVersionName() {
        String versionName = "";
        try {
            versionName = mContext.getPackageManager().getPackageInfo(
                    mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, e.toString());
        }
        return versionName;
    }

    // 获取本地IP函数
    public String getLocalIPAddress() {
        try {
            for (Enumeration<NetworkInterface> mEnumeration = NetworkInterface
                    .getNetworkInterfaces(); mEnumeration.hasMoreElements(); ) {
                NetworkInterface intf = mEnumeration.nextElement();
                for (Enumeration<InetAddress> enumIPAddr = intf
                        .getInetAddresses(); enumIPAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIPAddr.nextElement();
                    // 如果不是回环地址
                    if (!inetAddress.isLoopbackAddress()) {
                        // 直接返回本地IP地址
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e("Error", ex.toString());
        }
        return null;
    }


    public void removeSessionCookie(WebView webView) {
        CookieSyncManager.createInstance(mContext);
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().removeSessionCookie();
        webView.clearCache(true);
        webView.clearHistory();
    }

    /**
     * 清除webView的缓存文件
     *
     * @param dir
     * @param numDays
     * @return
     */
    public int clearCacheFolder(File dir, long numDays) {
        int deletedFiles = 0;
        if (dir != null && dir.isDirectory()) {
            try {
                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, numDays);
                    }
                    if (child.lastModified() < numDays) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deletedFiles;
    }

















}
