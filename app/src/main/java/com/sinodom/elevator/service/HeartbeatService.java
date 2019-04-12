package com.sinodom.elevator.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.db.Location;
import com.sinodom.elevator.single.ElevatorManager;
import com.sinodom.elevator.single.Server;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.util.retrofit2.RetrofitManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 心跳服务
 */
public class HeartbeatService extends Service {

    //心跳周期
    public static int mIntevalPeriod = 5 * 60 * 1000;
    protected RetrofitManager mRetrofitManager;
    public MyLocationListener mMyLocationListener;
    public static LocationClient mLocationClient;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRetrofitManager = new RetrofitManager();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //百度定位
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
        mLocationClient = new LocationClient(this.getApplicationContext());
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        InitLocation();
        //百度定位-end
        mLocationClient.start();
        return START_STICKY;
    }

    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        //设置定位模式
        //Hight_Accuracy:高精度定位模式下，会同时使用GPS、Wifi和基站定位，返回的是当前条件下精度最好的定位结果,
        //Battery_Saving:低功耗定位模式下，仅使用网络定位即Wifi和基站定位，返回的是当前条件下精度最好的网络定位结果,
        //Device_Sensors:仅用设备定位模式下，只使用用户的GPS进行定位。这个模式下，由于GPS芯片锁定需要时间，首次定位速度会需要一定的时间,
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //返回的定位结果是百度经纬度，gcj02(国测局加密经纬度坐标),bd09ll(百度加密经纬度坐标),bd09(百度加密墨卡托坐标)
        option.setCoorType("bd09ll");
        //设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
        option.setScanSpan(mIntevalPeriod);
        option.setIsNeedAddress(true);//反地理编码
        mLocationClient.setLocOption(option);
    }

    /**
     * 实现定位回调监听
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError
                    && !TextUtil.isEmpty(location.getAddrStr()) && location.getLatitude() != 4.9E-324 && location.getLongitude() != 4.9E-324
                    && location.getLatitude() != 0 && location.getLongitude() != 0) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                Location l = new Location();
                String guid = UUID.randomUUID().toString();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String ctime = formatter.format(cal.getTime());
                l.setGuid(guid);
                l.setUserId(ElevatorManager.getInstance().getSession().getUserID());
                l.setBaiduMapX(location.getLongitude() + "");
                l.setBaiduMapY(location.getLatitude() + "");
                l.setUpdateDate(ctime);
                l.setLongitudeAndLatitude(location.getLongitude() + "," + location.getLatitude());
                ElevatorManager.getInstance().addLocation(l);
                List<Location> locationList = ElevatorManager.getInstance().getLocationList(ElevatorManager.getInstance().getSession().getUserID());
                if (locationList != null && locationList.size() > 0) {
                    for (Location loca : locationList) {
                        upload(loca);
                    }
                }
            } else {
                if (location.getLocType() == 167) {
                    Toast.makeText(HeartbeatService.this, "服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void upload(final Location location) {
        Map<String, Object> map = new HashMap<>();
        map.put("UserId", location.getUserId());
        map.put("PhoneId", ElevatorManager.getInstance().getDeviceToken());
        map.put("BaiduMapX", location.getBaiduMapX());
        map.put("BaiduMapY", location.getBaiduMapY());
        map.put("LongitudeAndLatitude", location.getLongitudeAndLatitude());
        map.put("UpdateDate", location.getUpdateDate());
        Call<ResponBean> call = Server.getInstance().getService().saveLocation(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        ElevatorManager.getInstance().delLocation(location.getGuid());
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mRetrofitManager.cancelAll();
        mLocationClient.unRegisterLocationListener(mMyLocationListener);
        mLocationClient.stop();
    }
}
