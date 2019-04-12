package com.sinodom.elevator.activity.elevator.business.maintenance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.adapter.elist.HistoryDetailAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.business.HistoryDetailBean;

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

/**
 * Created by 安卓 on 2017/12/18.
 * 电梯维保历史详情
 */

public class HistoricalDetailsActivity1 extends BaseActivity {
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvComplete)
    TextView tvComplete;
    @BindView(R.id.tvShouldComplete)
    TextView tvShouldComplete;
    @BindView(R.id.lvRecord)
    PullToRefreshListView lvRecord;
    @BindView(R.id.tvNOData)
    TextView tvNOData;
    @BindView(R.id.bLoad)
    Button bLoad;
    @BindView(R.id.llNoData)
    LinearLayout llNoData;

    private List<HistoryDetailBean> mDataBeen = new ArrayList<>();
    private HistoryDetailAdapter mAdapter;
    private ListView mListView;
    private int pageNumber = 1;
    private Gson gson = new Gson();
    private String liftNum = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical_details1);
        ButterKnife.bind(this);
        liftNum = getIntent().getStringExtra("liftNum");
        init();

    }

    @OnClick({R.id.ivBack, R.id.bLoad})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.bLoad:
                showLoading("加载中...");
                rLoad();
                break;
        }
    }

    private void init() {

        lvRecord.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
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
                    loadData();
                } else {
                    lvRecord.onRefreshComplete();
                }
            }
        });
        mListView = lvRecord.getRefreshableView();
        mAdapter = new HistoryDetailAdapter(HistoricalDetailsActivity1.this, mDataBeen);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!lvRecord.isRefreshing()) {
                    if (!mDataBeen.get(position).getMaintenancePeriod().trim().equals("未维保")) {
                        Intent intent = new Intent();
                        intent.setClass(HistoricalDetailsActivity1.this, RecordDetailsActivity.class);
                        intent.putExtra("id", "" + mDataBeen.get(position).getID());
                        startActivity(intent);
                    }
                }
            }
        });

        showLoading("加载中...");
        rLoad();
    }

    private void initView() {
        tvShouldComplete.setText("" + mDataBeen.size());
        int a = 0;
        for (int i = 0; i < mDataBeen.size(); i++) {
            if (mDataBeen.get(i).getMaintenancePeriod().trim().equals("正常")) {
                a++;
            }
        }
        tvComplete.setText("" + a);
    }

    //加载更多
    private void loadData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("PageSize", "" + 30);
        map.put("PageIndex", "" + pageNumber);
        map.put("UserId", "" + userId);
        map.put("LiftNum", "" + URLEncoder.encode(liftNum));
        Logger.d("userId=" + userId + "——liftNum=" + liftNum);
        Call<ResponBean> call = server.getService().getCheckHistoryList(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                try {
                    if (response.body().isSuccess()) {
//                        pageNumber++;
                        List<HistoryDetailBean> mDataBeen2 = new ArrayList<>();
                        mDataBeen2.addAll((List<HistoryDetailBean>) gson.fromJson(response.body().getData(), new TypeToken<List<HistoryDetailBean>>() {
                        }.getType()));
                        mDataBeen.addAll(mDataBeen2);
                        mAdapter.setData(mDataBeen);
                        mAdapter.notifyDataSetChanged();
                        mListView.setVisibility(View.VISIBLE);
                        llNoData.setVisibility(View.GONE);
                        Logger.d("成功mDataBeen=" + mDataBeen.size());
                        initView();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mListView.setVisibility(View.GONE);
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
                    llNoData.setVisibility(View.VISIBLE);
                    showToast(parseError(throwable));
                    hideLoading();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
    }


    //刷新数据
    public void rLoad() {
        pageNumber = 1;
        mDataBeen.clear();
        loadData();
    }
}
