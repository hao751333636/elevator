package com.sinodom.elevator.fragment.inspect.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.inspect.RecordDisplayActivity;
import com.sinodom.elevator.adapter.elist.WorkRecordAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.inspect.InspectListBean;
import com.sinodom.elevator.fragment.BaseFragment;
import com.sinodom.elevator.util.PermissionUtil;
import com.sinodom.elevator.zxing.activity.CaptureActivity;
import com.tencent.bugly.beta.Beta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 安卓 on 2018/1/10.
 * 电梯维保--列表页
 */

public class InspectFragment extends BaseFragment {

    @BindView(R.id.bLoad)
    Button bLoad;
    @BindView(R.id.llNoData)
    LinearLayout llNoData;
    @BindView(R.id.lvRecord)
    PullToRefreshListView lvRecord;
    @BindView(R.id.main_zxing)
    ImageView mainZxing;

    private Gson gson = new Gson();

    private List<InspectListBean> mDataBeen = new ArrayList<>();
    private WorkRecordAdapter mAdapter;
    private ListView mListView;
    private int pageNumber = 1;
    private String userId = "";

    Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inspect, null);
        unbinder = ButterKnife.bind(this, view);
        userId = manager.getSession().getUserID() + "";
        Beta.checkUpgrade(false, false);
        init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void init() {

        lvRecord.setMode(PullToRefreshBase.Mode.BOTH);
        //设置刷新标签
        lvRecord.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
        //设置下拉标签
        lvRecord.getLoadingLayoutProxy().setPullLabel("准备刷新");
        //设置释放标签
        lvRecord.getLoadingLayoutProxy().setReleaseLabel("释放开始刷新");
        lvRecord.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //下拉刷新 业务代码
                    rLoad();
                } else if (refreshView.isFooterShown()) {
                    //上拉加载更多 业务代码
                    loadData();
                } else {
                    lvRecord.onRefreshComplete();
                }
            }
        });
        mListView = lvRecord.getRefreshableView();
        mAdapter = new WorkRecordAdapter(getActivity(), mDataBeen);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!lvRecord.isRefreshing()) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), RecordDisplayActivity.class);
                    intent.putExtra("result", "" + mDataBeen.get(position).getLiftNum());
                    startActivity(intent);
                }
            }
        });

        showLoading("加载中...");
        rLoad();
    }

    //加载更多
    private void loadData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("PageSize", "" + 10);
        map.put("PageIndex", "" + pageNumber);
        map.put("UserId", "" + userId);
        Call<ResponBean> call = server.getService().getInspectList(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
                        pageNumber++;
                        List<InspectListBean> mDataBeen2 = new ArrayList<>();
                        mDataBeen2.addAll((List<InspectListBean>) gson.fromJson(response.body().getData(), new TypeToken<List<InspectListBean>>() {
                        }.getType()));
                        mDataBeen.addAll(mDataBeen2);
                        mAdapter.setData(mDataBeen);
                        mAdapter.notifyDataSetChanged();
                        mListView.setVisibility(View.VISIBLE);
                        lvRecord.setVisibility(View.VISIBLE);
                        llNoData.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mListView.setVisibility(View.GONE);
                    lvRecord.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                }
                lvRecord.onRefreshComplete();
                hideLoading();
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    lvRecord.onRefreshComplete();
                    mListView.setVisibility(View.GONE);
                    lvRecord.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                    showToast(parseError(throwable));
                    hideLoading();
                }
            }
        });
    }

    //刷新数据
    public void rLoad() {
        pageNumber = 1;
        mDataBeen.clear();
        loadData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class), 0);
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                intent.putExtra("source", "1");
                startActivityForResult(intent, 0);
            } else {
                getPermission("请授权APP访问摄像头权限！");
            }

            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick({R.id.bLoad, R.id.main_zxing, R.id.ivBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                getActivity().finish();
                break;
            case R.id.bLoad:
                showLoading("加载中...");
                rLoad();
                break;
            case R.id.main_zxing:
                //6.0以上可以动态监测权限，6.0以下不能，但是可以通过Intent调用系统相机
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    boolean permission = selfPermissionGranted(Manifest.permission.CAMERA);
                    if (!permission) {
                        requestPermissions(
                                new String[]{Manifest.permission.CAMERA}, 100);
                    } else {
                        Intent intent = new Intent(getActivity(), CaptureActivity.class);
                        intent.putExtra("source", "1");
                        startActivityForResult(intent, 0);
                    }
                } else {
                    if (PermissionUtil.cameraIsCanUse()) {
                        Intent intent = new Intent(getActivity(), CaptureActivity.class);
                        intent.putExtra("source", "1");
                        startActivityForResult(intent, 0);
                    } else {
                        getPermission("缺少相机权限");
                    }
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
    }
}
