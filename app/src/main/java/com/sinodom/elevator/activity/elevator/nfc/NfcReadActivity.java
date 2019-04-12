package com.sinodom.elevator.activity.elevator.nfc;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.nfc.NfcReadBean;
import com.sinodom.elevator.util.HexUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NfcReadActivity extends BaseNfcActivity {

    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvContent1)
    TextView tvContent1;
    @BindView(R.id.tvDevice)
    TextView tvDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_read);
        ButterKnife.bind(this);
    }

    @Override
    public void cry(String type) {
        try {
            if (type.equals("phone")) {
                String str = readNdef();
                String text[] = str.split("`");
                tvContent.setText("电梯编号：" + text[0] + "\n"
                        + "注册代码：" + text[1] + "\n"
                        + "配件名称：" + text[2] + "\n"
                        + "电梯地址：" + text[3] + "\n"
                        + "绑定时间：" + text[4] + "\n"
                        + "唯一值：" + HexUtils.byte2HexStr(mTag.getId()).replace(" ", ""));
                loadData(HexUtils.byte2HexStr(mTag.getId()).replace(" ", ""));
            } else if (type.equals("deke")) {
                String text[] = content.split("`");
                tvContent.setText("电梯编号：" + text[0] + "\n"
                        + "注册代码：" + text[1] + "\n"
                        + "配件名称：" + text[2] + "\n"
                        + "电梯地址：" + text[3] + "\n"
                        + "绑定时间：" + text[4] + "\n"
                        + "唯一值：" + uid);
                loadData(uid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (type.equals("phone")) {
                String str = readNdef();
                tvContent.setText("标签值：" + str + "\n" + "唯一值：" + HexUtils.byte2HexStr(mTag.getId()).replace(" ", ""));
                loadData(HexUtils.byte2HexStr(mTag.getId()).replace(" ", ""));
            } else if (type.equals("deke")) {
                tvContent.setText("标签值：" + content + "\n" + "唯一值：" + uid);
                loadData(uid);
            }
        }
    }

    private void loadData(final String num) {
        showLoading("加载中...");
        Call<ResponBean> call = server.getService().getTL_LiftPartsNFC(num);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        NfcReadBean bean = new Gson().fromJson(response.body().getData(), NfcReadBean.class);
                        String text = "电梯编号：" + bean.getLiftNum() + "\n"
                                + "注册代码：" + bean.getCertificateNum() + "\n"
                                + "配件名称：" + bean.getProductName() + "\n"
                                + "电梯地址：" + bean.getInstallationAddress() + "\n"
                                + "初始化时间：" + bean.getCreateTime().replace("T", " ").substring(0, 19) + "\n"
                                + "唯一值：" + num;
                        tvContent1.setText(text);
                    } else {
                        tvContent1.setText(response.body().getMessage());
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("查询失败！");
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

    @Override
    public void onDevice(String str) {
        super.onDevice(str);
        tvDevice.setText(str);
    }

    @OnClick({R.id.ivBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }
}