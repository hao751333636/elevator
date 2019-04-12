package com.sinodom.elevator.activity.elevator.business.complaint;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.bean.rescue.ComplaintBean;
import com.sinodom.elevator.util.PhoneUtil;
import com.sinodom.elevator.util.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 投诉建议详情
 */
public class ComplaintDetailActivity extends BaseActivity {
    @BindView(R.id.tvAdviceTypeDict)
    TextView tvAdviceTypeDict;
    @BindView(R.id.tvAdviceStatusDict)
    TextView tvAdviceStatusDict;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvRemark)
    TextView tvRemark;
    @BindView(R.id.tvContactName)
    TextView tvContactName;
    @BindView(R.id.tvContactPhone)
    TextView tvContactPhone;
    @BindView(R.id.tvCreateUser)
    TextView tvCreateUser;
    @BindView(R.id.tvLiftNum)
    TextView tvLiftNum;
    private ComplaintBean mBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mBean = (ComplaintBean) getIntent().getSerializableExtra("bean");
        tvAdviceTypeDict.setText(mBean.getAdviceTypeDict().getDictName());
        tvAdviceStatusDict.setText(mBean.getAdviceStatusDict().getDictName());
        tvTitle.setText(mBean.getTitle());
        tvRemark.setText(mBean.getRemark());
        tvContactName.setText(mBean.getContactName());
        tvContactPhone.setText(mBean.getContactPhone());
        tvCreateUser.setText(mBean.getCreateUser().getUserName());
        tvLiftNum.setText(mBean.getLiftNum() + "");
    }

    @OnClick({R.id.ivBack, R.id.llContactPhone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.llContactPhone:
                if (!TextUtil.isEmpty(mBean.getContactPhone())) {
                    PhoneUtil.call(context, mBean.getContactPhone());
                } else {
                    showToast("电话为空！");
                }
                break;
        }
    }
}
