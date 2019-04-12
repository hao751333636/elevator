package com.sinodom.elevator.fragment.elevator.rescue.rescuelist;

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
import com.sinodom.elevator.activity.elevator.rescue.PhotoActivity;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.adapter.elist.RescueListAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.rescue.RescueBean;
import com.sinodom.elevator.fragment.BaseFragment;
import com.sinodom.elevator.fragment.elevator.rescue.RescueFragment;

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
 * 电梯救援-救援列表-尚未接单
 */
public class NotYetFragment extends BaseFragment implements BaseAdapter.OnItemClickListener, BaseAdapter.OnClickListener {

    @BindView(R.id.pull_refresh_list)
    PullToRefreshListView pullRefreshList;
    Unbinder unbinder;
    @BindView(R.id.tvNOData)
    TextView tvNOData;
    @BindView(R.id.bLoad)
    Button bLoad;
    @BindView(R.id.llNoData)
    LinearLayout llNoData;
    private ListView mListView;
    private RescueListAdapter mAdapter;
    private List<RescueBean> mList = new ArrayList<>();
    private Gson gson = new Gson();
    private int pageNumber = 1;
    public boolean isStart = false;
    public boolean isRefresh = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rescue_list, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        //PullToRefreshListView-start
        pullRefreshList.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
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
        mAdapter = new RescueListAdapter(context);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnClickListener(this);
        mListView.setAdapter(mAdapter);
        if (!isStart) {
            showLoading("加载中...");
            rLoad();
            isStart = true;
        }
    }

    private void loadData() {
        Map<String, Object> map = new HashMap<>();
        map.put("UserId", manager.getSession().getUserID() + "");
        Call<ResponBean> call = server.getService().getTaskList(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                isRefresh = false;
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        List<RescueBean> list = gson.fromJson(response.body().getData(), new TypeToken<List<RescueBean>>() {
                        }.getType());
                        for (RescueBean bean : list) {
                            if (bean.getRescueType() == 0) {
                                mList.add(bean);
                            }
                        }
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
                isRefresh = false;
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
            RescueFragment fragment = (RescueFragment) NotYetFragment.this.getParentFragment();
            com.sinodom.elevator.fragment.elevator.main.RescueFragment parentFragment = (com.sinodom.elevator.fragment.elevator.main.RescueFragment) fragment.getParentFragment();
            parentFragment.setFragment(mList.get(position).getID());
        }
    }

    @Override
    public void onClick(View v, int position) {
        Intent intent = new Intent(context, PhotoActivity.class);
        intent.putExtra("LiftId", mList.get(position).getLiftId());
        intent.putExtra("TaskId", mList.get(position).getID());
        startActivity(intent);
    }

    //刷新数据
    public void rLoad() {
        isRefresh = true;
        pageNumber = 1;
        mList.clear();
        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mRetrofitManager.cancelAll();
    }

    @OnClick(R.id.bLoad)
    public void onViewClicked() {
        showLoading("加载中...");
        rLoad();
    }
}
