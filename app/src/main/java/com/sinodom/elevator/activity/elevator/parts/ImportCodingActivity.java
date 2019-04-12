package com.sinodom.elevator.activity.elevator.parts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.activity.inspect.LiftAddActivity;

import java.net.URLDecoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 安卓 on 2017/12/7.
 * 输入编号入口
 */

public class ImportCodingActivity extends BaseActivity {

    @BindView(R.id.etCoding)
    EditText etCoding;
    private String result, inspectId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_coding2);
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
                    Intent intent = new Intent(ImportCodingActivity.this, PartsActivity.class);
                    intent.putExtra("liftNum", "" + URLDecoder.decode(inspectId));
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