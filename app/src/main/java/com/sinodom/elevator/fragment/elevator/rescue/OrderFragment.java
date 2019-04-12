package com.sinodom.elevator.fragment.elevator.rescue;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.lift.LiftBean;
import com.sinodom.elevator.fragment.BaseFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 安卓 on 2017/11/14.
 * 电梯救援-人工下单
 */

public class OrderFragment extends BaseFragment {

    @BindView(R.id.etLiftNumber)
    EditText etLiftNumber;
    @BindView(R.id.tvLiftAddress)
    TextView tvLiftAddress;
    @BindView(R.id.etNumber)
    EditText etNumber;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etDescribed)
    EditText etDescribed;
    @BindView(R.id.tvCommit)
    TextView tvCommit;
    Unbinder unbinder;
    private Gson gson = new Gson();
    private LiftBean bean;
    public static boolean isRefresh = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        // 对EditText内容的实时监听
//        etLiftNumber.addTextChangedListener(new TextWatcher() {
//
//            // 第二个执行
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            // 第一个执行
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            // 第三个执行
//            @Override
//            public void afterTextChanged(Editable s) { // Edittext中实时的内容
//                if (s.length() >= 6) {
//                    getLift(s.toString().trim());
//                }
//            }
//        });
        etLiftNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    if (!hasFocus && etLiftNumber.getText().toString().trim().length() >= 6) {
                        getLift(etLiftNumber.getText().toString().trim());
                    } else {
                        tvLiftAddress.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick(R.id.tvCommit)
    public void onViewClicked() {
        CharSequence etLiftNumberCs = etLiftNumber.getText();
        if (TextUtils.isEmpty(etLiftNumberCs.toString())) {
            showToast("请输入电梯编号！");
            return;
        }
        CharSequence tvLiftAddressCs = tvLiftAddress.getText();
        if (TextUtils.isEmpty(tvLiftAddressCs.toString())) {
            showToast("请输入正确电梯编号！");
            return;
        }
        CharSequence etNumberCs = etNumber.getText();
        if (TextUtils.isEmpty(etNumberCs.toString()) && Integer.valueOf(etNumberCs.toString()) > 0) {
            showToast("请输入被困人数并且大于0！");
            return;
        }
        CharSequence etPhoneCs = etPhone.getText();
        if (TextUtils.isEmpty(etPhoneCs.toString())) {
            showToast("请输入困人电话！");
            return;
        }
        CharSequence etDescribedCs = etDescribed.getText();
        if (TextUtils.isEmpty(etDescribedCs.toString())) {
            showToast("请输入来电备注！");
            return;
        }
        showLoading("提交中...");
        commit();
    }

    public void getLift(String lift) {
        Map<String, Object> map = new HashMap<>();
        map.put("UserId", manager.getSession().getUserID());
        Call<ResponBean> call = server.getService().getLiftByLiftNum(map, lift);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        showToast(response.body().getMessage());
                        bean = gson.fromJson(response.body().getData(), LiftBean.class);
                        tvLiftAddress.setText(bean.getInstallationAddress());
                    } else {
                        showToast(response.body().getMessage());
                        tvLiftAddress.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    tvLiftAddress.setText("");
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    hideLoading();
                    showToast(parseError(throwable));
                    tvLiftAddress.setText("");
                }
            }
        });
    }

    //提交数据
    private void commit() {
        Map<String, Object> map = new HashMap<>();
        map.put("LiftId", bean.getID());
        map.put("RescuePhone", etPhone.getText().toString().trim());
        map.put("RescueNumber", etNumber.getText().toString().trim());
        map.put("Content", etDescribed.getText().toString().trim());
        map.put("ConfirmUserId", userId);
        Call<ResponBean> call = server.getService().artificialOrder(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        showToast(response.body().getMessage());
                        etLiftNumber.setText("");
                        tvLiftAddress.setText("");
                        etPhone.setText("");
                        etNumber.setText("");
                        etDescribed.setText("");
                        isRefresh = true;
                    } else {
                        showToast(response.body().getMessage());
                        isRefresh = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("提交失败！");
                }
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
    }

}
