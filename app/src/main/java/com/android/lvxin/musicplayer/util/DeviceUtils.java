package com.android.lvxin.musicplayer.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.android.lvxin.musicplayer.R;

import java.util.List;
import java.util.Locale;

/**
 * The type Device utils.
 */
public class DeviceUtils {
    /**
     * get build model
     *
     * @return
     */
    public static String getBuildModel() {
        String model = Build.MODEL.toLowerCase(Locale.getDefault());
        return model;
    }


    public static String getIMEI(Context context) {
        TelephonyManager service = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = service.getDeviceId();
        return imei;
    }

    public static String getDeviceInfo(Context context) {

        TelephonyManager service = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String seperator = "|";
        String imei = service.getDeviceId();

        StringBuffer sb = new StringBuffer();
        sb.append("IMEI=").append(imei).append(seperator).append("VERSION=")
                .append(getVersionCode(context)).append(seperator).append("SNAME=")
                .append(service.getNetworkOperatorName()).append(seperator).append("DEVICE=")
                .append(Build.MODEL).append(seperator).append("OSVERSION=")
                .append(Build.VERSION.RELEASE);
        return sb.toString();
    }

    public static String getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "0.0.0";
        }
    }

    /**
     * <pre>
     *  获取网络类型
     * </pre>
     *
     * @param context
     * @return
     */
    public static String getNetworkType(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return "NETTYPE=" + info.getTypeName();
        } else {
            return "";
        }
    }

    /**
     * 检查网络
     *
     * @param context
     * @return
     */
    public static boolean checkNetWork(Context context) {
        if (!DeviceUtils.checkNetEnv(context)) {
            Toast.makeText(context, R.string.network_not_available, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 检测网络是否连接
     *
     * @return
     */
    public static boolean isNetworkAvailable(Context mContext) {
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            return manager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    /**
     * 检查网络
     *
     * @param context
     * @return
     */
    public static boolean checkNetEnv(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo.State mobile = null;
        NetworkInfo.State wifi = null;
        NetworkInfo mobileInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileInfo != null) {
            mobile = mobileInfo.getState();
        }
        NetworkInfo wifiInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null) {
            wifi = wifiInfo.getState();
        }
        if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
            return true;
        }
        if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
            return true;
        }
        return false;
    }

    /**
     * 是否连接WiFi
     *
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static boolean isTopActivity(Context context, String packageName) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }

}
