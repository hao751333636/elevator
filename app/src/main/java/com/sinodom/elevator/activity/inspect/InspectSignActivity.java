package com.sinodom.elevator.activity.inspect;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.util.PictureUtil;
import com.sinodom.elevator.view.LinePathView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 检验签字
 */
public class InspectSignActivity extends BaseActivity {

    @BindView(R.id.lpView)
    LinePathView lpView;
    @BindView(R.id.tvType)
    TextView tvType;
    private AlertDialog.Builder alertDialog;
    private int currentJWhich = 0;
    private String[] jItems;
    private int mId;
    private String mTypeName;
    private int mTypeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_sign);
        ButterKnife.bind(this);
        mId = getIntent().getIntExtra("id", 0);
        mTypeId = getIntent().getIntExtra("typeId", 0);
        mTypeName = getIntent().getStringExtra("typeName");
        alertDialog = new AlertDialog.Builder(context);
        jItems = new String[]{"制动器试验", "限速器校验", "平衡系数校验"};
//        tvType.setText(jItems[currentJWhich]);
        tvType.setText(mTypeName);
    }

    @OnClick({R.id.ivBack, R.id.ivScanning, R.id.tvClear, R.id.tvCommit, R.id.llType})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.ivScanning:
                break;
            case R.id.tvClear:
                lpView.clear();
                break;
            case R.id.tvCommit:
                sign();
                break;
            case R.id.llType:
//                alertDialog.setSingleChoiceItems(jItems, currentJWhich,
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (currentJWhich != which) {
//                                    currentJWhich = which;
//                                    tvType.setText(jItems[currentJWhich]);
//                                }
//                                dialog.dismiss();
//                            }
//                        }
//                ).show();
                break;
        }
    }

    private void sign() {
        try {
            if (lpView.getTouched()) {
                String path = lpView.save();
                upload(PictureUtil.bitmapToString(path));
            } else {
                showToast("还没有签名！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void upload(String url) {
        showLoading("加载中...");
        Map<String, Object> map = new HashMap<>();
        map.put("InspectId", "" + mId);
        map.put("SignUrl", "" + url);
//        map.put("TypeId", "" + currentJWhich + 1);
        map.put("TypeId", "" + mTypeId);
        Call<ResponBean> call = server.getService().saveInspectSign(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    Gson gson = new Gson();
                    Logger.d("" + gson.toJson(response.body()));
                    if (response.body().isSuccess()) {
//                    ("提交成功！");
                        showToast("" + response.body().getMessage());
                        if (response.body().getMessage().equals("操作成功")) {
                            setResult(Constants.Code.SCORE_OK);
                            finish();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("提交失败");
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    hideLoading();
                    showToast(parseError(throwable));
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        mRetrofitManager.cancelAll();
        super.onDestroy();
    }
}