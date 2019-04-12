package com.sinodom.elevator.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.db.Inspect;
import com.sinodom.elevator.db.Maintenance;
import com.sinodom.elevator.db.NfcBind;
import com.sinodom.elevator.db.PaperlessMaintenance;
import com.sinodom.elevator.single.ElevatorManager;
import com.sinodom.elevator.single.Server;
import com.sinodom.elevator.util.retrofit2.RetrofitManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 数据上传服务AsyncUploadService
 */
public class AsyncUploadService extends Service {

    private int interval = 1000 * 60 * 1; //间隔时间-1分钟
    protected RetrofitManager mRetrofitManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRetrofitManager = new RetrofitManager();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new AsyncUploadRunnable(this)).start();
        return START_STICKY;
    }

    @Override
    public ComponentName startService(Intent service) {
        return super.startService(service);
    }

    private class AsyncUploadRunnable implements Runnable {

        private Context context;

        public AsyncUploadRunnable(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            while (true) {
                List<Inspect> inspectList = ElevatorManager.getInstance().getInspectList();
                if (inspectList != null && inspectList.size() > 0) {
                    Logger.d("AsyncUploadService异步上传服务:开始上传“检验”数据");
                    for (Inspect inspect : inspectList) {
                        uploadFile(inspect);
                    }
                } else {
                    Logger.d("AsyncUploadService异步上传服务:暂无“检验”数据");
                }
                List<Maintenance> maintenanceList = ElevatorManager.getInstance().getMaintenanceList();
                if (maintenanceList != null && maintenanceList.size() > 0) {
                    Logger.d("AsyncUploadService异步上传服务:开始上传“维保”数据");
                    for (Maintenance maintenance : maintenanceList) {
                        saveCheckDetails(maintenance);
                    }
                } else {
                    Logger.d("AsyncUploadService异步上传服务:暂无“维保”数据");
                }
                List<PaperlessMaintenance> maintenanceList1 = ElevatorManager.getInstance().getPaperlessMaintenanceList();
                if (maintenanceList1 != null && maintenanceList1.size() > 0) {
                    Logger.d("AsyncUploadService异步上传服务:开始上传“无纸化维保”数据");
                    for (PaperlessMaintenance maintenance : maintenanceList1) {
                        savePaperlessMaintenanceDetails(maintenance);
                    }
                } else {
                    Logger.d("AsyncUploadService异步上传服务:暂无“无纸化维保”数据");
                }
                List<NfcBind> nfcBind = ElevatorManager.getInstance().getNfcBindList();
                if (nfcBind != null && nfcBind.size() > 0) {
                    Logger.d("AsyncUploadService异步上传服务:开始上传“nfc绑定”数据");
                    for (NfcBind bean : nfcBind) {
                        saveNfcBindDetails(bean);
                    }
                } else {
                    Logger.d("AsyncUploadService异步上传服务:暂无“nfc绑定”数据");
                }
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Logger.e(e.getMessage());
                }
            }
        }
    }

    //检验-上传文件
    public void uploadFile(final Inspect inspect) {
        String s = inspect.getLocal();
        //构建要上传的文件
        File file = new File(s);
        RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
        Call<ResponBean> call = Server.getInstance().getService().uploadFile(part);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        String data = response.body().getData();
                        Logger.d("成功 data = " + data);
                        commit(inspect, data);
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    Logger.d("失败");
                }
            }
        });
    }

    //检验-提交数据
    private void commit(final Inspect inspect, final String file) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("UserId", ElevatorManager.getInstance().getSession().getUserID());
        map.put("InspectId", inspect.getInspectId());    //id
        map.put("TypeID", inspect.getTypeID());
        map.put("TypeName", inspect.getTypeName());
        map.put("StepId", inspect.getStepId());
        map.put("StepName", inspect.getStepName());
        map.put("IsPassed", inspect.getIsPassed());  // 是否合格
        map.put("Remark", inspect.getRemark());   //备注
        //上传照片还是视频
        if (inspect.getIsPhoto()) {
            map.put("PhotoUrl", file);
            map.put("VideoPath", "");
        } else {
            map.put("PhotoUrl", "");
            map.put("VideoPath", file);
        }
        //定位
        map.put("MapX", "" + inspect.getMapX());
        map.put("MapY", "" + inspect.getMapY());
        Call<ResponBean> call = Server.getInstance().getService().saveInspectDetail(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        if (response.body().getMessage().equals("操作成功")) {
                            Logger.d("检验异步上传服务成功");
                            ElevatorManager.getInstance().delInspectByKey(inspect.getId());
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "离线检验数据提交成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Logger.d("检验异步上传服务失败");
                        }
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.d("检验异步上传服务失败");
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    Logger.d("检验异步上传服务失败");
                }
            }
        });
    }

    //维保-提交数据
    private void saveCheckDetails(final Maintenance maintenance) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), maintenance.getJson());
        Call<ResponBean> call = Server.getInstance().getService().saveCheckDetails(body);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Logger.d("维保异步上传服务成功");
                        ElevatorManager.getInstance().delMaintenanceByKey(maintenance.getId());
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "离线维保数据提交成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Logger.d("维保异步上传服务失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.d("检验异步上传服务失败");
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    Logger.d("维保异步上传服务失败");
                }
            }
        });
    }

    //无纸化维保-提交数据
    private void savePaperlessMaintenanceDetails(final PaperlessMaintenance maintenance) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), maintenance.getJson());
        Call<ResponBean> call = Server.getInstance().getService().saveNFCCheckDetails(body);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Logger.d("无纸化维保异步上传服务成功");
                        ElevatorManager.getInstance().delPaperlessMaintenanceByKey(maintenance.getId());
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "离线无纸化维保数据提交成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Logger.d("无纸化维保异步上传服务失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.d("无纸化维保异步上传服务失败");
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    Logger.d("无纸化维保异步上传服务失败");
                }
            }
        });
    }

    //nfc绑定-提交数据
    private void saveNfcBindDetails(final NfcBind maintenance) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), maintenance.getJson());
        Call<ResponBean> call = Server.getInstance().getService().bindCheckNFC(body);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Logger.d("nfc绑定异步上传服务成功");
                        ElevatorManager.getInstance().delNfcBindByKey(maintenance.getId());
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), "离线nfc绑定数据提交成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Logger.d("nfc绑定异步上传服务失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Logger.d("nfc绑定异步上传服务失败");
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    Logger.d("nfc绑定异步上传服务失败");
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mRetrofitManager.cancelAll();
        try {
            Intent intent = new Intent(this, AsyncUploadService.class);
            startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}