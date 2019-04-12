package com.sinodom.elevator.activity.elevator;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.activity.elevator.business.sign.SignDetailActivity;
import com.sinodom.elevator.bean.sys.GetWebViewBean;
import com.sinodom.elevator.util.PermissionUtil;
import com.sinodom.elevator.util.PhoneUtil;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.zxing.activity.CaptureActivity;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BridgeWebViewActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivScanning)
    ImageView ivScanning;
    @BindView(R.id.actionbar)
    RelativeLayout actionbar;
    @BindView(R.id.webview)
    BridgeWebView mWebView;
    @BindView(R.id.tvError)
    TextView tvError;
    @BindView(R.id.bFinish)
    Button bFinish;
    @BindView(R.id.llError)
    LinearLayout llError;
    private String url = "";
    private String title = "";
    private String msg = "";
    private String source = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge_web_view);
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
        initWebView();
        showLoading("加载中...");
    }

    private void initWebView() {
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        mWebView.setWebContentsDebuggingEnabled(true);
        mWebView.setDefaultHandler(new DefaultHandler());
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    hideLoading();
                }
            }
        });
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        mWebView.loadUrl(url);

        mWebView.registerHandler("back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (!TextUtil.isEmpty(data)) {
                    setResult(Constants.Code.WBAZTS_OK, new Intent().putExtra("type", data));
                }
                finish();
            }
        });

        mWebView.registerHandler("toast", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                showToast(data);
            }
        });

        mWebView.registerHandler("showloading", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                showLoading(data);
            }
        });

        mWebView.registerHandler("hideloading", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                hideLoading();
            }
        });

        mWebView.registerHandler("call", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (TextUtils.isEmpty(data)) {
                    showToast("电话号为空！");
                } else {
                    PhoneUtil.call(context, data);
                }
            }
        });

        mWebView.registerHandler("getUserID", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                function.onCallBack(manager.getSession().getUserID() + "");
            }
        });

        mWebView.registerHandler("getWebView", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                GetWebViewBean bean = new Gson().fromJson(data, GetWebViewBean.class);
                Intent intent = new Intent(context, BridgeWebViewActivity.class);
                intent.putExtra("url", bean.getLink());
                intent.putExtra("title", bean.getTitle());
                startActivityForResult(intent, Constants.Code.GO_WBAZTS);
            }
        });

        mWebView.registerHandler("reload", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                mWebView.reload();
            }
        });

        mWebView.registerHandler("sign", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent();
                intent.setClass(context, SignDetailActivity.class);
                intent.putExtra("id", data);
                startActivityForResult(intent, Constants.Code.GO_RESCUE);
            }
        });
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
                        ActivityCompat.requestPermissions(BridgeWebViewActivity.this,
                                new String[]{Manifest.permission.CAMERA}, 200);
                    } else {
                        Intent intent = new Intent(BridgeWebViewActivity.this, CaptureActivity.class);
                        intent.putExtra("source", source);
                        startActivityForResult(intent, Constants.Code.GO_SCORE);
//                        startActivity(intent);
                    }
                } else {
                    if (PermissionUtil.cameraIsCanUse()) {
                        Intent intent = new Intent(BridgeWebViewActivity.this, CaptureActivity.class);
                        intent.putExtra("source", source);
                        startActivityForResult(intent, Constants.Code.GO_SCORE);
//                        startActivity(intent);
                    } else {
                        getPermission("缺少相机权限");
                    }
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫码返回
        if (requestCode == Constants.Code.GO_SCORE && resultCode == Constants.Code.SCORE_OK) {
            mWebView.reload(); //刷新
        }
        //返回刷新
        if (requestCode == Constants.Code.GO_WBAZTS && resultCode == Constants.Code.WBAZTS_OK) {
            mWebView.callHandler("reload", data.getStringExtra("type"), new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                }
            });
        }
        //签认刷新
        if (requestCode == Constants.Code.GO_RESCUE && resultCode == Constants.Code.RESCUE_OK) {
            mWebView.callHandler("reload", "3", new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(BridgeWebViewActivity.this, CaptureActivity.class);
                intent.putExtra("source", source);
                startActivityForResult(intent, Constants.Code.GO_SCORE);
            } else {
                getPermission("请授权APP访问摄像头权限！");
            }

            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void clearWebViewCache() {
        // 清除cookie即可彻底清除缓存
        CookieSyncManager.createInstance(context);
        CookieManager.getInstance().removeAllCookie();
    }

    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearCache(true);
            mWebView.clearHistory();
            clearWebViewCache();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
