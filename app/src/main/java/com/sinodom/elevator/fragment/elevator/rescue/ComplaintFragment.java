package com.sinodom.elevator.fragment.elevator.rescue;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.elevator.business.complaint.ComplaintDetailActivity;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.adapter.elist.ComplaintAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.rescue.ComplaintBean;
import com.sinodom.elevator.fragment.BaseFragment;

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
 * Created by 安卓 on 2017/11/14.
 * 电梯救援-投诉建议
 */

public class ComplaintFragment extends BaseFragment implements BaseAdapter.OnItemClickListener {

    @BindView(R.id.pull_refresh_list)
    PullToRefreshListView pullRefreshList;
    @BindView(R.id.tvNOData)
    TextView tvNOData;
    @BindView(R.id.bLoad)
    Button bLoad;
    @BindView(R.id.llNoData)
    LinearLayout llNoData;
    Unbinder unbinder;
    private ListView mListView;
    private ComplaintAdapter mAdapter;
    private List<ComplaintBean> mList = new ArrayList<>();
    private Gson gson = new Gson();
    private int mPageIndex = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaint, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        //PullToRefreshListView-start
        pullRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
        //设置刷新标签
        pullRefreshList.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
        //设置下拉标签
        pullRefreshList.getLoadingLayoutProxy().setPullLabel("准备刷新");
        //设置释放标签
        pullRefreshList.getLoadingLayoutProxy().setReleaseLabel("释放开始刷新");
        pullRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //下拉刷新 业务代码
                    rLoad();
                } else if (refreshView.isFooterShown()) {
                    //上拉加载更多 业务代码
                    loadData();
                } else {
                    pullRefreshList.onRefreshComplete();
                }
            }
        });
        mListView = pullRefreshList.getRefreshableView();
        mAdapter = new ComplaintAdapter(context);
        mAdapter.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);
        showLoading("加载中...");
        rLoad();
    }

    private void loadData() {
        Map<String, Object> map = new HashMap<>();
        map.put("PageSize", 20);
        map.put("PageIndex", mPageIndex);
        Call<ResponBean> call = server.getService().getAdviceList(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        List<ComplaintBean> list = gson.fromJson(response.body().getData(), new TypeToken<List<ComplaintBean>>() {
                        }.getType());
                        mPageIndex = mPageIndex + 1;
                        mList.addAll(list);
                        mAdapter.setData(mList);
                        mAdapter.notifyDataSetChanged();
                        mListView.setVisibility(View.VISIBLE);
                        llNoData.setVisibility(View.GONE);
                    } else {
                        mListView.setVisibility(View.GONE);
                        llNoData.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mListView.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                }
                pullRefreshList.onRefreshComplete();
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    pullRefreshList.onRefreshComplete();
                    mListView.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                    showToast(parseError(throwable));
                    hideLoading();
                }
            }
        });

    }

    @Override
    public void onItemClick(View v, int position) {
        if (!pullRefreshList.isRefreshing()) {
            Intent intent = new Intent(context, ComplaintDetailActivity.class);
            intent.putExtra("bean", mList.get(position));
            startActivity(intent);
        }
    }

    //刷新数据
    public void rLoad() {
        mPageIndex = 1;
        mList.clear();
        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bLoad)
    public void onViewClicked() {
        showLoading("加载中...");
        rLoad();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRetrofitManager.cancelAll();
    }
}