package com.sinodom.elevator.activity.inspect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.bean.ResponBean;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 安卓 on 2017/12/7.
 * 输入编号入口
 */

public class ImportCodingActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.etCoding)
    EditText etCoding;
    @BindView(R.id.tvYes)
    TextView tvYes;
    @BindView(R.id.tvCreate)
    TextView tvCreate;

    private String result, inspectId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_coding);
        ButterKnife.bind(this);
        result = getIntent().getStringExtra("result");
        if (result != null && !result.equals("")) {
            inspectId = result.substring(result.indexOf("=") + 1);
            etCoding.setText(inspectId);
        }
    }

    @OnClick({R.id.ivBack, R.id.tvYes, R.id.tvCreate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvYes:
                if (etCoding.getText() != null && !etCoding.getText().toString().trim().equals("")) {
                    inspectId = "" + etCoding.getText().toString().trim();
//                    userDataHttp_info();
                    Intent intent = new Intent(ImportCodingActivity.this, InspectChooseActivity.class);
                    intent.putExtra("inspectId", "" + URLDecoder.decode(inspectId));
                    setResult(Constants.Code.SCORE_OK);
                    startActivityForResult(intent, 1);
                } else {
                    showToast("请输入电梯编码");
                }
                break;
            case R.id.tvCreate:
                Intent intent = new Intent(this, LiftAddActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void userDataHttp_info() {
        inspectId = "" + etCoding.getText().toString().trim();
        showLoading("加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("LiftNum", "" + URLEncoder.encode(inspectId));
        map.put("UserId", "" + userId);

        Call<ResponBean> call = server.getService().getIsInspectByLiftNum(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        if (response.body().getMessage().equals("操作成功")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("inspectId", "" + inspectId);
//                        Intent intent = new Intent(ImportCodingActivity.this, InspectActivity.class);
                            Intent intent = new Intent(ImportCodingActivity.this, InspectChooseActivity.class);
                            intent.putExtras(bundle);
                            startActivityForResult(intent, 1);
                        } else if (response.body().getMessage().equals("任务不存在")) {
                            Establish();
                        } else {
                            showToast("" + response.body().getMessage());
                        }
                    } else {
                        showToast("电梯不存在！");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("电梯不存在！");
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

    private void Establish() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(this);
        normalDialog.setCancelable(false);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("暂无此电梯信息，是否创建？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        normalDialog.create();
                        Bundle bundle = new Bundle();
                        bundle.putString("inspectId", "" + inspectId);
//                        startActivity(new Intent(ImportCodingActivity.this, InspectActivity.class).putExtras(bundle));
//                        Intent intent = new Intent(ImportCodingActivity.this, InspectActivity.class);
                        Intent intent = new Intent(ImportCodingActivity.this, InspectChooseActivity.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 1);
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        normalDialog.create();
                    }
                });
        // 显示
        normalDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 判断请求码和返回码是不是正确的，这两个码都是我们自己设置的
        if (requestCode == 1 && resultCode == 10) {
            // 把得到的数据显示到输入框内
            finish();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
    }
}
