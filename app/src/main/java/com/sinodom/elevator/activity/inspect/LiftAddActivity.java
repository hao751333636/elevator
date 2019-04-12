package com.sinodom.elevator.activity.inspect;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.bean.ResponBean;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 创建电梯
 */
public class LiftAddActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.etCoding)
    EditText etCoding;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.tvYes)
    TextView tvYes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lift_add);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivBack, R.id.tvYes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvYes:
                CharSequence etCodingCs = etCoding.getText();
                if (TextUtils.isEmpty(etCodingCs.toString())) {
                    showToast("请填写注册代码");
                    return;
                }
                CharSequence etAddressCs = etAddress.getText();
                if (TextUtils.isEmpty(etAddressCs.toString())) {
                    showToast("请填写电梯地址");
                    return;
                }
                commit();
                break;
        }
    }

    private void commit() {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<>();
        map.put("CertificateNum", etCoding.getText().toString().trim());
        map.put("InstallationAddress", etAddress.getText().toString().trim());
        Call<ResponBean> call = server.getService().addLift(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        showToast(response.body().getMessage());
                        finish();
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("注册失败！");
                }
                hideLoading();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
    }
}
