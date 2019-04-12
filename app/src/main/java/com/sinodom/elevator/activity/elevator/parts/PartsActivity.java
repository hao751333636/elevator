package com.sinodom.elevator.activity.elevator.parts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.adapter.elist.PartsAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.parts.PartsBean;

import java.net.URLEncoder;
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

public class PartsActivity extends BaseActivity {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvEleInfoNum)
    TextView tvEleInfoNum;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvCode)
    TextView tvCode;
    @BindView(R.id.tvRegistrationCode)
    TextView tvRegistrationCode;
    @BindView(R.id.llAdd)
    LinearLayout llAdd;
    @BindView(R.id.listView)
    ListView listView;
    private String mLiftNum;
    private PartsBean mBean;
    private List<PartsBean.ListTypeBean> mBeanShow = new ArrayList<>();
    private PartsAdapter mAdapter;
    private AlertDialog.Builder alertDialog;
    //检验项选择-弹出显示项
    private String[] jItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts);
        ButterKnife.bind(this);
        mLiftNum = getIntent().getStringExtra("liftNum");
        alertDialog = new AlertDialog.Builder(context);
        mAdapter = new PartsAdapter(context);
        listView.setAdapter(mAdapter);
        mAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View v, final int position) {
                new AlertDialog.Builder(context)
                        .setTitle("确定删除")
                        .setMessage("是否要删除？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteData(position);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return true;
            }
        });
        loadData();
        showLoading("加载中...");
    }

    private void loadData() {
        showLoading("加载中...");
        Map<String, String> map = new HashMap<String, String>();
        map.put("LiftNum", "" + URLEncoder.encode(mLiftNum));
        Call<ResponBean> call = server.getService().getLift(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        Gson gson = new Gson();
                        Logger.json(gson.toJson(response.body()));
                        mBean = gson.fromJson(response.body().getData(), PartsBean.class);
                        if (mBean != null) {
                            showToast("加载成功！");
                            tvEleInfoNum.setText(mBean.getLiftNum());
                            tvAddress.setText(mBean.getInstallationAddress());
                            tvRegistrationCode.setText(mBean.getCertificateNum());
                            mAdapter.setData(mBean.getList());
                            mAdapter.notifyDataSetChanged();
                        } else {
                            showToast("暂无此电梯信息");
                        }
                    } else {
                        showToast(response.body().getMessage());
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("加载失败！");
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

    private void deleteData(int position) {
        showLoading("加载中...");
        Call<ResponBean> call = server.getService().deleteTL_Parts(mBean.getList().get(position).getID());
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        showToast(response.body().getMessage());
                        loadData();
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("加载失败！");
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

    @OnClick({R.id.ivBack, R.id.tvOffLine, R.id.llAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvOffLine:
                if (mBean != null) {
                    Intent intent = new Intent(context, PartsOffLineActivity.class);
                    intent.putExtra("bean", mBean);
                    startActivityForResult(intent, Constants.Code.GO_SCORE);
                }
                break;
            case R.id.llAdd:
                if (mBean != null) {
                    mBeanShow.clear();
                    for (int i = 0; i < mBean.getListType().size(); i++) {
                        if (manager.getPartsItem(mBean.getLiftId(), mBean.getListType().get(i).getID()) == null) {
                            mBeanShow.add(mBean.getListType().get(i));
                        }
                    }
                    if (mBeanShow.size() > 0) {
                        jItems = new String[mBeanShow.size()];
                        for (int i = 0; i < mBeanShow.size(); i++) {
                            jItems[i] = mBeanShow.get(i).getPartsName();
                        }
                        alertDialog.setSingleChoiceItems(jItems, -1,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(context, NfcBindActivity.class);
                                        intent.putExtra("bean", mBeanShow.get(which));
                                        intent.putExtra("LiftId", mBean.getLiftId());
                                        intent.putExtra("liftBean", mBean);
                                        startActivityForResult(intent, Constants.Code.GO_SCORE);
                                        dialog.dismiss();
                                    }
                                }
                        ).show();
                    } else {
                        showToast("无可绑定配件！");
                    }
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.Code.GO_SCORE && resultCode == Constants.Code.SCORE_OK) {
            loadData();
            showLoading("加载中...");
        }
    }
}
