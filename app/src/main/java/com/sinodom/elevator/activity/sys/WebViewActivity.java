package com.sinodom.elevator.activity.sys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.view.ProgressWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 安卓 on 2018/1/3.
 * 公用WebView
 */
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.webView)
    ProgressWebView mWebView;
    private String Title = "";
    private String Url = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Title = getIntent().getStringExtra("Title");
        Url = getIntent().getStringExtra("Url");
        tvTitle.setText(Title);
        //WebView加载web资源
        mWebView.getSettings().setJavaScriptEnabled(true);//支持Javascript
        mWebView.getSettings().setBuiltInZoomControls(true);//设置支持缩放
        mWebView.getSettings().setDisplayZoomControls(false); //隐藏原生的缩放控件
        mWebView.getSettings().setSupportZoom(true);//设置是否支持变焦
        mWebView.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放
        mWebView.getSettings().setLoadWithOverviewMode(true);//打开页面时， 自适应屏幕
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);//滚动条不占位
        mWebView.getSettings().setAllowFileAccess(true); // 允许访问文件
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setDomStorageEnabled(true);//允许DCOM
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        //百分比，100就是不变，100以下就是缩小
        mWebView.setInitialScale(100);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl(Url+"?id=" + userId);
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

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onDestroy() {
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
