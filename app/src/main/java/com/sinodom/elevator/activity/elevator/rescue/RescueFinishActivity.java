package com.sinodom.elevator.activity.elevator.rescue;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.adapter.elist.WbTypeAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.rescue.DictBean;
import com.sinodom.elevator.util.DisplayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 救援完成
 */
public class RescueFinishActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.etDescribed)
    EditText etDescribed;
    @BindView(R.id.tvFault)
    TextView tvFault;
    @BindView(R.id.llFault)
    LinearLayout llFault;
    @BindView(R.id.etFault)
    EditText etFault;
    @BindView(R.id.tvRescue)
    TextView tvRescue;
    @BindView(R.id.llRescue)
    LinearLayout llRescue;
    @BindView(R.id.etRescue)
    EditText etRescue;
    @BindView(R.id.tvCommit)
    TextView tvCommit;
    @BindView(R.id.etNumber)
    EditText etNumber;
    @BindView(R.id.etPhone)
    EditText etPhone;
    private PopupWindow mTypePopupWindow;
    private List<DictBean> mFaultList = new ArrayList<>();
    private List<DictBean> mRescueList = new ArrayList<>();
    private String mFaultId;
    private String mRescueId;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue_finish);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(Constants.Code.RESCUE_OK, new Intent());
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.ivBack, R.id.llFault, R.id.llRescue, R.id.tvCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                setResult(Constants.Code.RESCUE_OK, new Intent());
                finish();
                break;
            case R.id.llFault:
                if (mFaultList == null || mFaultList.size() == 0) {
                    showLoading("加载中...");
                    loadFaultdata();
                } else {
                    showTypePopupWindow(llFault, true);
                }
                break;
            case R.id.llRescue:
                if (mRescueList == null || mRescueList.size() == 0) {
                    showLoading("加载中...");
                    loadRescueData();
                } else {
                    showTypePopupWindow(llRescue, false);
                }
                break;
            case R.id.tvCommit:
//                CharSequence etNumberCs = etNumber.getText();
//                if (TextUtils.isEmpty(etNumberCs.toString())) {
//                    showToast("请填写被困人数");
//                    return;
//                }
//                CharSequence etPhoneCs = etPhone.getText();
//                if (TextUtils.isEmpty(etPhoneCs.toString())) {
//                    showToast("请填写困人电话");
//                    return;
//                }
//                CharSequence etDescribedCs = etDescribed.getText();
//                if (TextUtils.isEmpty(etDescribedCs.toString())) {
//                    showToast("请填写警情描述");
//                    return;
//                }
                CharSequence tvFaultCs = tvFault.getText();
                if (TextUtils.isEmpty(tvFaultCs.toString())) {
                    showToast("请选择故障原因");
                    return;
                }
//                CharSequence etFaultCs = etFault.getText();
//                if (TextUtils.isEmpty(etFaultCs.toString())) {
//                    showToast("请填写故障原因");
//                    return;
//                }
                CharSequence tvRescueCs = tvRescue.getText();
                if (TextUtils.isEmpty(tvRescueCs.toString())) {
                    showToast("请选择解救方法");
                    return;
                }
//                CharSequence etRescueCs = etRescue.getText();
//                if (TextUtils.isEmpty(etRescueCs.toString())) {
//                    showToast("请填写解救方法");
//                    return;
//                }
                Intent intent = new Intent();
                intent.putExtra("RescueNumber", etNumber.getText().toString().trim());
                intent.putExtra("RescuePhone", etPhone.getText().toString().trim());
                intent.putExtra("Content", etDescribed.getText().toString().trim());
                intent.putExtra("ReasonId", mFaultId);
                intent.putExtra("ReasonDesc", etFault.getText().toString().trim());
                intent.putExtra("RemedyId", mRescueId);
                intent.putExtra("RemedyDesc", etRescue.getText().toString().trim());
                setResult(Constants.Code.RESCUE_OK, intent);
                finish();
                break;
        }
    }

    //加载数据
    private void loadRescueData() {
        Map<String, Object> map = new HashMap<>();
        map.put("UserId", manager.getSession().getUserID() + "");
        Call<ResponBean> call = server.getService().getDictListByRoot(map, "RescueMethod");
        mRetrofitManager.call(call,new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        List<DictBean> list = gson.fromJson(response.body().getData(), new TypeToken<List<DictBean>>() {
                        }.getType());
                        mRescueList.clear();
                        mRescueList.addAll(list);
                        showTypePopupWindow(llRescue, false);
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if(!call.isCanceled()) {
                    hideLoading();
                    showToast(parseError(throwable));
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
    }

    //加载数据
    private void loadFaultdata() {
        Map<String, Object> map = new HashMap<>();
        map.put("UserId", manager.getSession().getUserID() + "");
        Call<ResponBean> call = server.getService().getDictListByRoot(map, "FailureCause");
        mRetrofitManager.call(call,new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        List<DictBean> list = gson.fromJson(response.body().getData(), new TypeToken<List<DictBean>>() {
                        }.getType());
                        mFaultList.clear();
                        mFaultList.addAll(list);
                        showTypePopupWindow(llFault, true);
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if(!call.isCanceled()) {
                    hideLoading();
                    showToast(parseError(throwable));
                }
            }
        });
    }

    private void showTypePopupWindow(View view, final boolean isFault) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.popup_wb_type, null);
        ListView listView = (ListView) contentView.findViewById(R.id.listView);
        WbTypeAdapter adapter = new WbTypeAdapter(context);
        listView.setAdapter(adapter);
        if (isFault) {
            adapter.setData(mFaultList);
        } else {
            adapter.setData(mRescueList);
        }
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                mTypePopupWindow.dismiss();
                if (isFault) {
                    mFaultId = mFaultList.get(position).getID();
                    tvFault.setText(mFaultList.get(position).getFullPath());
                } else {
                    mRescueId = mRescueList.get(position).getID();
                    tvRescue.setText(mRescueList.get(position).getFullPath());
                }
            }
        });
        mTypePopupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(context, 150), true);

        mTypePopupWindow.setTouchable(true);

        mTypePopupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        mTypePopupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.color.white));

        // 设置好参数之后再show
        mTypePopupWindow.showAsDropDown(view, 0, DisplayUtil.dip2px(context, 1));
        mTypePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
//                Drawable nav_up = getResources().getDrawable(R.mipmap.ic_fangchan_choice);
//                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
//                tvType.setCompoundDrawables(null, null, nav_up, null);
            }
        });
    }
}
