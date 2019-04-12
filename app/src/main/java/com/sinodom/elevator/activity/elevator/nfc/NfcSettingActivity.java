package com.sinodom.elevator.activity.elevator.nfc;

import android.os.Bundle;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.util.HexUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 标签初始化
 */
public class NfcSettingActivity extends BaseNfcActivity {

    @BindView(R.id.tvNfcType)
    TextView tvNfcType;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvDevice)
    TextView tvDevice;
    private String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_setting);
        ButterKnife.bind(this);
        setText("dianti119-000000001");
        tvNfcType.setText("标签初始化");

    }

    @Override
    public void cry(String type) {
        if (type.equals("phone")) {
            str = readNdef();
            bind(str, HexUtils.byte2HexStr(mTag.getId()).replace(" ", ""));
            tvContent.setText("标签值：" + str + "\n" + "唯一值：" + HexUtils.byte2HexStr(mTag.getId()).replace(" ", ""));
            writeNdef("dianti119-000000001");
        } else if (type.equals("deke")) {
            bind(content, uid);
            tvContent.setText("标签值：" + content + "\n" + "唯一值：" + uid);
        }
    }

    private void bind(final String str, String uid) {
        showLoading("提交中...");
        final Map<String, Object> map = new HashMap<>();
        map.put("NFCCode", str);
        map.put("NFCNum", uid);
        map.put("UserId", userId);
        Call<ResponBean> call = server.getService().saveCheckNfc(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        showToast(response.body().getMessage());
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("提交失败");
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

    @Override
    public void onCard(String str) {
        super.onCard(str);
    }

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        finish();
    }
}