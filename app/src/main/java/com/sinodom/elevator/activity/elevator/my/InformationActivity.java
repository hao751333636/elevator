package com.sinodom.elevator.activity.elevator.my;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.sys.InformationBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我的信息
 */
public class InformationActivity extends BaseActivity {

    @BindView(R.id.tvLoginName)
    TextView tvLoginName;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvModel)
    TextView tvModel;
    @BindView(R.id.tvDeptName)
    TextView tvDeptName;
    @BindView(R.id.tvRoleName)
    TextView tvRoleName;
    @BindView(R.id.tvRemark)
    TextView tvRemark;
    private Gson gson = new Gson();
    private InformationBean mBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        showLoading("加载中...");
        loadData();
    }

    private void loadData() {
        Call<ResponBean> call = server.getService().getUserInfo(userId);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        mBean = gson.fromJson(response.body().getData(), InformationBean.class);
                        tvLoginName.setText(mBean.getLoginName());
                        tvUserName.setText(mBean.getUserName());
                        tvEmail.setText(mBean.getEmail());
                        tvPhone.setText(mBean.getPhone());
                        tvModel.setText(mBean.getMobile());
                        tvDeptName.setText(mBean.getDept().getDeptName());
                        tvRoleName.setText(mBean.getRoleName());
                        tvRemark.setText(mBean.getRemark());
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
