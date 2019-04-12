package com.sinodom.elevator.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.sinodom.elevator.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {

    //手机号正则判断
    public static boolean isMobileNO(String mobiles) {
//        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Pattern p = Pattern.compile("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static void call(Context context, String tel) {
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        context.startActivity(intent);
    }

    public static String getUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }

    //网络连接判断
    public static boolean isNetworkConnect(Context context) {
        ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cwjManager.getActiveNetworkInfo();
        if (networkInfo != null && NetworkInfo.State.CONNECTED == networkInfo.getState() && networkInfo.isAvailable()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                //Logger.d("WIFI网络连接");
                return true;
            } else //Logger.d("移动网络连接");
                return networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            return false;
        }
    }



    /**
     * 检查互联网地址是否可以访问-使用get请求
     *
     * @param urlStr   要检查的url
     * @param callback 检查结果回调（是否可以get请求成功）{@see java.lang.Comparable<T>}
     */
    public static void isNetWorkAvailableOfGet(final String urlStr, final Comparable<Boolean> callback) {
        final Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (callback != null) {
                    callback.compareTo(msg.arg1 == 0);
                }
            }

        };
        new Thread(new Runnable() {

            @Override
            public void run() {
                Message msg = new Message();
                try {
                    Connection conn = new Connection(urlStr);
                    Thread thread = new Thread(conn);
                    thread.start();
                    thread.join(3 * 1000); // 设置等待DNS解析线程响应时间为3秒
                    int resCode = conn.get(); // 获取get请求responseCode
                    msg.arg1 = resCode == 200 ? 0 : -1;
                } catch (Exception e) {
                    msg.arg1 = -1;
                    e.printStackTrace();
                } finally {
                    handler.sendMessage(msg);
                }
            }

        }).start();
    }

    /**
     * HttpURLConnection请求线程
     */
    private static class Connection implements Runnable {
        private String urlStr;
        private int responseCode;

        public Connection(String urlStr) {
            this.urlStr = urlStr;
        }

        public void run() {
            try {
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                set(conn.getResponseCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public synchronized void set(int responseCode) {
            this.responseCode = responseCode;
        }

        public synchronized int get() {
            return responseCode;
        }
    }

    /* @author suncat
     * @category 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     * @return
     */
    public static final boolean ping() {

        String result = null;
        try {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 100 " + ip);// ping网址1次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null) {
                stringBuffer.append(content);
            }
            Log.d("------ping-----", "result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } finally {
            Log.d("----result---", "result = " + result);
        }
        return false;
    }

    public static boolean isNet(Context context) {
        boolean isConn = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            isConn = !(activeNetworkInfo == null || !activeNetworkInfo.isConnected());
        } else {
            Logger.e("Can't get connectivitManager");
        }
        return isConn;
    }

    public static boolean isCellNet(Context context) {
        ConnectivityManager mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mMobile = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean flag = false;
        if (mMobile != null) {
            if (mMobile.isAvailable()) {
                if (mMobile.isConnected()) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public static boolean isWifiNet(Context context) {
        ConnectivityManager mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean flag = false;
        if (mWifi != null) {
            if (mWifi.isAvailable()) {
                if (mWifi.isConnected()) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public static String getDataPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        if (sdDir != null) {
            return sdDir.toString() + "/" + Constants.PathSet.DATA_PATH;
        } else {
            return "";
        }
    }

    public static String getTempDataPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        if (sdDir != null) {
            return sdDir.toString() + "/" + Constants.PathSet.TEMP_DATA_PATH;
        } else {
            return "";
        }
    }

    public static String getTempPhotoPath() {
        File sdDir = null;
        String path = "";
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        if (sdDir != null) {
            path = sdDir.toString() + "/" + Constants.PathSet.TEMP_DATA_PATH;
            File out = new File(path);
            if (!out.exists()) {
                out.mkdir(); // 创建文件夹
            }
            return path;
        } else {
            return "";
        }
    }

    public static String getVolleyCachePath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        if (sdDir != null) {
            return sdDir.toString() + "/" + Constants.PathSet.VOLLEY_CACHE_PATH;
        } else {
            return "";
        }
    }

    public static String getSaveImageFilePath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        if (sdDir != null) {
            return sdDir.toString() + "/" + Constants.PathSet.SAVE_IMAGE_PATH + DateUtil.getTs() + ".jpg";
        } else {
            return "";
        }
    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    public static int getScreenDpi(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.densityDpi;
    }

    public static void vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(-1);
    }

}
