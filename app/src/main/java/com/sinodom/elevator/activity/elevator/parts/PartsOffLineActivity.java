package com.sinodom.elevator.activity.elevator.parts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.Constants;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.adapter.elist.PartsOffLineAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.parts.PartsBean;
import com.sinodom.elevator.db.Parts;
import com.sinodom.elevator.single.ApiService;
import com.sinodom.elevator.util.TextUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PartsOffLineActivity extends BaseActivity {

    @BindView(R.id.tvEleInfoNum)
    TextView tvEleInfoNum;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvRegistrationCode)
    TextView tvRegistrationCode;
    @BindView(R.id.listView)
    ListView listView;
    private PartsBean mBean;
    private List<Parts> mList = new ArrayList<>();
    private PartsOffLineAdapter mAdapter;
    private String mLog[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts_off_line);
        ButterKnife.bind(this);
        mBean = (PartsBean) getIntent().getSerializableExtra("bean");
        tvEleInfoNum.setText(mBean.getLiftNum());
        tvAddress.setText(mBean.getInstallationAddress());
        tvRegistrationCode.setText(mBean.getCertificateNum());
        mList.addAll(manager.getPartsList(mBean.getLiftId()+""));
        mAdapter = new PartsOffLineAdapter(context);
        listView.setAdapter(mAdapter);
        mAdapter.setData(mList);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View v, final int position) {
                new AlertDialog.Builder(context)
                        .setTitle("确定删除")
                        .setMessage("是否要删除？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                manager.delPartsByKey(mList.get(position).getId());
                                mList.clear();
                                mList.addAll(manager.getPartsList(mBean.getLiftId()+""));
                                mAdapter.setData(mList);
                                mAdapter.notifyDataSetChanged();
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
    }


    private boolean commit[];

    @OnClick({R.id.ivBack, R.id.tvCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                setResult(Constants.Code.SCORE_OK);
                finish();
                break;
            case R.id.tvCommit:
                if (commit != null && commit.length > 0) {
                    for (boolean isCommit : commit) {
                        if (!isCommit) {
                            showToast("正在提交中...");
                            return;
                        }
                    }
                }
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(this);
                normalDialog.setCancelable(false);
                normalDialog.setTitle("您确定要一键离线传输吗？");
                normalDialog.setMessage("请您确保手机网络畅通");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                                normalDialog.create();
                                mLog = new String[mList.size()];
                                for (int i = 0; i < mList.size(); i++) {
                                    commit = new boolean[mList.size()];
                                    addLiftParts(i, mList.get(i));
                                }
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
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            setResult(Constants.Code.SCORE_OK);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    //上传文件
    public void addLiftParts(final int position, final Parts bean) {
        showLoading("提交中...");
        //构建要上传的文件
        File file = new File(bean.getPhotoPath());
        RequestBody Manufacturer = RequestBody.create(MediaType.parse("multipart/form-data"), bean.getManufacturer());
        RequestBody Brand = RequestBody.create(MediaType.parse("multipart/form-data"), bean.getBrand());
        RequestBody Model = RequestBody.create(MediaType.parse("multipart/form-data"), bean.getModel());
        RequestBody InstallationTime = RequestBody.create(MediaType.parse("multipart/form-data"), bean.getInstallationTime());
        RequestBody PartsTypeId = RequestBody.create(MediaType.parse("multipart/form-data"), bean.getPartsTypeId());
        RequestBody ProductName = RequestBody.create(MediaType.parse("multipart/form-data"), bean.getProductName());
        RequestBody LiftId = RequestBody.create(MediaType.parse("multipart/form-data"), bean.getLiftId());
        RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), body);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER)
                .client(new OkHttpClient.Builder().
                        connectTimeout(60 * 10, TimeUnit.SECONDS).
                        readTimeout(60 * 10, TimeUnit.SECONDS).
                        writeTimeout(60 * 10, TimeUnit.SECONDS).build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService service = retrofit.create(ApiService.class);
        Call<ResponBean> call = service.addLiftParts(Manufacturer, Brand, Model, InstallationTime, PartsTypeId, ProductName, LiftId, part);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                commit[position] = true;
                try {
                    if (response.body().isSuccess()) {
                        showToast(response.body().getMessage());
                    } else {
                        showToast(response.body().getMessage());
                    }
                    manager.delPartsByKey(bean.getId());
                    mList.clear();
                    mList.addAll(manager.getPartsList(mBean.getLiftId()+""));
                    mAdapter.setData(mList);
                    mAdapter.notifyDataSetChanged();
                    mLog[position] = bean.getProductName() + "：" + response.body().getMessage();
                    boolean isTrue = false;
                    for (String log : mLog) {
                        if (TextUtil.isEmpty(log)) {
                            isTrue = true;
                            break;
                        }
                    }
                    if (!isTrue) {
                        String showLog = "";
                        for (String log : mLog) {
                            showLog = showLog + log + "\n";
                        }
                        final AlertDialog.Builder normalDialog =
                                new AlertDialog.Builder(context);
                        normalDialog.setCancelable(false);
                        normalDialog.setTitle("一键离线传输结果");
                        normalDialog.setMessage(showLog);
                        normalDialog.setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //...To-do
                                        normalDialog.create();
                                        setResult(Constants.Code.SCORE_OK);
                                        finish();
                                    }
                                });
                        // 显示
                        normalDialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("上传失败");
                }
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    commit[position] = true;
                    Logger.d("失败");
                    hideLoading();
                    showToast(parseError(throwable));
                }
            }
        });
    }
}
