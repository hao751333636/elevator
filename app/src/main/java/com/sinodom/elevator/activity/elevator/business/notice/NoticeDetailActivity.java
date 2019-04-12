package com.sinodom.elevator.activity.elevator.business.notice;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.notice.NoticeBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 通知通告详情
 */
public class NoticeDetailActivity extends BaseActivity {

    @BindView(R.id.tvContent)
    TextView tvContent;
    private NoticeBean mBean;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mBean = (NoticeBean) getIntent().getSerializableExtra("bean");
        showLoading("加载中...");
        loadData();
    }

    private void loadData() {
        Map<String, Object> map = new HashMap<>();
        map.put("UserId", manager.getSession().getUserID());
        Call<ResponBean> call = server.getService().getArticle(map, mBean.getID() + "", manager.getSession().getUserID() + "");
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        NoticeBean bean = gson.fromJson(response.body().getData(), NoticeBean.class);
                        tvContent.setText(bean.getContent() != null ? Html.fromHtml(bean.getContent()) : "");
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    showToast(parseError(throwable));
                    hideLoading();
                }
            }
        });

    }

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
    }
}
