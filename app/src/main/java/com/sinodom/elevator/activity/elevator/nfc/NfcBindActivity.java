package com.sinodom.elevator.activity.elevator.nfc;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.db.NfcBind;
import com.sinodom.elevator.util.HexUtils;
import com.sinodom.elevator.util.TextUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * NFC绑定
 */
public class NfcBindActivity extends BaseNfcActivity {

    @BindView(R.id.tvNfcType)
    TextView tvNfcType;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvDevice)
    TextView tvDevice;
    private String mTypeName;
    private String mTvContent;
    private int mLiftId;
    private int mCheckTermId;
    private String mCheckTermName;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndef);
        ButterKnife.bind(this);
        mTypeName = getIntent().getStringExtra("typeName");
        mTvContent = getIntent().getStringExtra("typeContent");
        mLiftId = getIntent().getIntExtra("LiftId", 0);
        mCheckTermId = getIntent().getIntExtra("CheckTermId", 0);
        mCheckTermName = getIntent().getStringExtra("CheckTermName");
        tvNfcType.setText(mTypeName);
        tvContent.setText(mTvContent);

    }

    @Override
    public void cry(String type) {
        if (type.equals("phone")) {
            String str = readNdef();
            if (!TextUtil.isEmpty(str)) {
                bind(str, HexUtils.byte2HexStr(mTag.getId()).replace(" ", ""));
            } else {
                showToast("绑定失败！");
            }
            tvContent.setText("标签值：" + str + "\n" + "唯一值：" + HexUtils.byte2HexStr(mTag.getId()).replace(" ", ""));
        } else if (type.equals("deke")) {
            if (!TextUtil.isEmpty(content)) {
                bind(content, uid);
            } else {
                showToast("绑定失败！");
            }
            tvContent.setText("标签值：" + content + "\n" + "唯一值：" + uid);
        }
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

    private void bind(final String str, String uid) {
        showLoading("绑定中...");
        final Map<String, Object> map = new HashMap<>();
        map.put("LiftId", mLiftId);
        map.put("NFCCode", str);
        map.put("CheckTermName", mCheckTermName);
        map.put("NFCNum", uid);
        Call<ResponBean> call = server.getService().bindCheckNFC(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        showToast(response.body().getMessage());
                        setResult(Constants.Code.SCORE_OK);
                        finish();
                    } else {
                        showToast("绑定失败！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("绑定失败！");
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    showToast(parseError(throwable));
                    hideLoading();
                    NfcBind nfcBind = new NfcBind();
                    nfcBind.setJson(new Gson().toJson(map).toString());
                    Logger.d(new Gson().toJson(map).toString());
                    manager.addNfcBind(nfcBind);
                    showToast("离线提交成功");
//                    setResult(Constants.Code.SCORE_OK);
                    finish();
                }
            }
        });

    }

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
//        try {
//            //读写文本Demo，不带进度回调
//            boolean isSuc = ntag21x.NdefTextWrite("dianti119-000000001");
//            if (isSuc) {
//                showToast("写入成功");
//            } else {
//                showToast("写入失败");
//            }
//        } catch (CardNoResponseException e) {
//            e.printStackTrace();
//        }
//        writeNdef("dianti119-000000001");
        finish();
    }
}