package com.sinodom.elevator.activity.elevator;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.util.AppUtil;
import com.sinodom.elevator.util.ImageUtil;
import com.sinodom.elevator.util.PermissionUtil;
import com.sinodom.elevator.util.PictureUtil;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.view.MyAlertDialog;
import com.sinodom.elevator.zxing.activity.CaptureActivity;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.actionbar)
    RelativeLayout actionbar;
    @BindView(R.id.ivScanning)
    ImageView ivScanning;
    private String url = "";
    private String title = "";
    private String msg = "";
    private String source = "";
    private PullToRefreshWebView mPullRefreshWebView;
    private WebView mWebView;
    public MyLocationListener mMyLocationListener;
    public LocationClient mLocationClient;

    public ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mUploadMessage2;
    public static final int FILECHOOSER_RESULTCODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
        msg = intent.getStringExtra("msg");
        source = intent.getStringExtra("source");
        if (TextUtil.isEmpty(title)) {
            actionbar.setVisibility(View.GONE);
        } else {
            actionbar.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
        if (TextUtil.isEmpty(source)) {
            ivScanning.setVisibility(View.GONE);
        } else {
            ivScanning.setVisibility(View.VISIBLE);
        }
        //百度定位
        mLocationClient = new LocationClient(this);
        mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        InitLocation();
        //百度定位-end
        Logger.d("url=" + url);
        initWebView();
        showLoading(TextUtils.isEmpty(msg) ? "加载中..." : msg);
    }

    private void initWebView() {
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);//支持Javascript
        //mWebView.getSettings().setBuiltInZoomControls(true);//设置支持缩放
//        mWebView.getSettings().setSupportZoom(true);//设置是否支持变焦
        mWebView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放
        mWebView.getSettings().setLoadWithOverviewMode(true);//打开页面时， 自适应屏幕
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);//滚动条不占位
        mWebView.getSettings().setAllowFileAccess(true); // 允许访问文件
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setDomStorageEnabled(true);//允许DCOM
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        //百分比，100就是不变，100以下就是缩小
        mWebView.setInitialScale(100);
        //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                hideLoading();
                //location();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //mWebView.setVisibility(View.GONE);
                //llError.setVisibility(View.VISIBLE);
            }

            //当网页面加载失败时，会调用 这个方法，所以我们在这个方法中处理
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                //mWebView.setVisibility(View.GONE);
                //llError.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                //mWebView.setVisibility(View.GONE);
                //llError.setVisibility(View.VISIBLE);
            }
        });

        mWebView.setDownloadListener(new com.tencent.smtt.sdk.DownloadListener() {

            @Override
            public void onDownloadStart(String arg0, String arg1, String arg2, String arg3, long arg4) {
                Logger.d("url=" + arg0);
                Uri uri = Uri.parse(arg0);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
//        mWebView.setWebChromeClient(new WebChromeClient(){
//
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                tvTitle.setText(title);
//            }
//        });
        // 添加一个对象, 让JS可以访问该对象的方法, 该对象中可以调用JS中的方法
        mWebView.addJavascriptInterface(new App(this), "app");
        //mWebView.setWebChromeClient(new XHSWebChromeClient());
        //WebView加载web资源
        mWebView.loadUrl(url);
    }

    @OnClick({R.id.ivBack, R.id.ivScanning})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivScanning:
                //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    boolean permission = selfPermissionGranted(Manifest.permission.CAMERA);
                    if (!permission) {
                        ActivityCompat.requestPermissions(WebViewActivity.this,
                                new String[]{Manifest.permission.CAMERA}, 200);
                    } else {
                        Intent intent = new Intent(WebViewActivity.this, CaptureActivity.class);
                        intent.putExtra("source", source);
//                    startActivityForResult(intent,0);
                        startActivity(intent);
                    }
                } else {
                    if (PermissionUtil.cameraIsCanUse()) {
                        Intent intent = new Intent(WebViewActivity.this, CaptureActivity.class);
                        intent.putExtra("source", source);
//                    startActivityForResult(intent,0);
                        startActivity(intent);
                    } else {
                        getPermission("缺少相机权限");
                    }
                }
                break;
        }
    }

    public class App {

        Context mContxt;

        public App(Context mContxt) {
            this.mContxt = mContxt;
        }

        //JavaScript调用此方法返回，sdk17版本以上加上注解
        @JavascriptInterface
        public void back() {
            finish();
        }

        @JavascriptInterface
        public void toast(String msg) {
            showToast(msg);
        }

        @JavascriptInterface
        public void showloading(String msg) {
            showLoading(msg);
        }

        @JavascriptInterface
        public void hideloading() {
            hideLoading();
        }

        @JavascriptInterface
        public void getWebView(String str, String title) {
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("url", str);
            intent.putExtra("title", title);
            startActivity(intent);
        }

        @JavascriptInterface
        public String getUserID() {
            try {
                return manager.getSession().getUserID() + "";
            } catch (Exception e) {
                return "00000000-0000-0000-0000-000000000000";
            }
        }

        @JavascriptInterface
        public void getZxing() {
            //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean permission = selfPermissionGranted(Manifest.permission.CAMERA);
                if (!permission) {
                    ActivityCompat.requestPermissions(WebViewActivity.this,
                            new String[]{Manifest.permission.CAMERA}, 200);
                } else {
                    Intent intent = new Intent(WebViewActivity.this, CaptureActivity.class);
                    intent.putExtra("source", source);
//                    startActivityForResult(intent,0);
                    startActivity(intent);
                }
            } else {
                if (PermissionUtil.cameraIsCanUse()) {
                    Intent intent = new Intent(WebViewActivity.this, CaptureActivity.class);
                    intent.putExtra("source", source);
//                    startActivityForResult(intent,0);
                    startActivity(intent);
                } else {
                    getPermission("缺少相机权限");
                }
            }
        }

        @JavascriptInterface
        public void getPhoto() {
            AppUtil.openGallery(activity);
//            PackageManager pm1 = getPackageManager();
//            boolean permission2 = (PackageManager.PERMISSION_GRANTED == pm1.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", getPackageName()));
//            boolean permission3 = (PackageManager.PERMISSION_GRANTED == pm1.checkPermission("android.permission.CAMERA", getPackageName()));
//            if (permission2 == true && permission3 == true) {
//                AppUtil.openCamera(activity);
//            } else {
//                alertDialog();
//            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            //拍照1或2
            if (requestCode == Constants.Code.OPEN_GALLERY || requestCode == Constants.Code.OPEN_CAMERA) {
                if (resultCode == Activity.RESULT_OK) {
                    String path = "";
                    Uri uri = intent.getData();
                    if (uri != null) {
                        //相册选择
                        //file:///storage/sdcard0/Pictures/lc/small_IMG_20150925_161335.jpg
                        path = ImageUtil.getPickPhotoPath(context, uri);
                        String realpath = path.replace(Constants.ResProtocol.FILE, "");
                        String photoBase64 = "";
                        int degree = ImageUtil.readPictureDegree(realpath);
                        if (degree == 0) {
                            photoBase64 = PictureUtil.bitmapToString(realpath);
                        } else {
                            photoBase64 = PictureUtil.bitmapToStringRotateToDegrees(realpath, degree);
                        }
                        Logger.d(photoBase64);
                        mWebView.loadUrl("javascript:photoBase64('" + photoBase64 + "')");
                        //mWebView.loadUrl("javascript:photoBase64(" + "222222" + ")");
                    }
                }
            }
        } catch (Exception e) {
            showToast("照片获取异常");
            Logger.e(e.getMessage());
        }
    }

    public void location() {
        boolean permission = selfPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION);
        boolean permission1 = selfPermissionGranted(Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permission == true && permission1 == true) {
            startLocationClient();
        } else {
            ActivityCompat.requestPermissions(WebViewActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationClient();
            } else {
                getPermission("请授权APP获取位置信息权限！");
            }
            return;
        }
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(WebViewActivity.this, CaptureActivity.class);
                intent.putExtra("source", source);
//                startActivityForResult(intent,0);
                startActivity(intent);
            } else {
                getPermission("请授权APP访问摄像头权限！");
            }

            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void startLocationClient() {
        showLoading("定位中...");
        mLocationClient.start();
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
        option.setScanSpan(0);
        option.setIsNeedAddress(true);//反地理编码
        mLocationClient.setLocOption(option);
    }

    private void alertDialog(String text) {
        MyAlertDialog myAlertDialog = new MyAlertDialog(context, R.style.DialogStyle);
        myAlertDialog.setTitle(text);
        myAlertDialog.setBCancelGone();
        myAlertDialog.setOnConfirmListener(new MyAlertDialog.OnClickListener() {
            @Override
            public void onClick(MyAlertDialog dialog, View v) {
                dialog.dismiss();
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(localIntent);
            }
        });
        myAlertDialog.show();
    }

    /**
     * 实现定位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            hideLoading();
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                Logger.d("latitude=" + location.getLatitude() + "|longitude=" + location.getLongitude() + "|address=" + location.getAddrStr());
                //showToast("javascript:alert(\"" + location.getLatitude() + "|" + location.getLongitude()+"\")");
                //mWebView.loadUrl("javascript:alert(\"" + location.getLatitude() + "|" + location.getLongitude()+"\")");
                //mWebView.loadUrl("javascript:app.toast(\"" + location.getLatitude() + "|" + location.getLongitude() + "\")");
            } else {
                if (location.getLocType() == 167) {
                    Toast.makeText(context, "服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。", Toast.LENGTH_LONG).show();
                }
                Logger.d("百度定位失败");
                hideLoading();
            }
            if (mLocationClient != null && mLocationClient.isStarted()) {
                mLocationClient.stop();
            }
        }
    }

    //按返回键时,不退出程序而是返回上一浏览页面：
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onStop() {
        mLocationClient.stop();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mLocationClient.stop();
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();

            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}