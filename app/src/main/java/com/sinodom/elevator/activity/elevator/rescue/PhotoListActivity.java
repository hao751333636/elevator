package com.sinodom.elevator.activity.elevator.rescue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.activity.elevator.ViewPagerActivity;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.adapter.PhotoGridViewAdapter;
import com.sinodom.elevator.adapter.elist.NfcBindAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.lift.PhotoBean;
import com.sinodom.elevator.view.NoScrollGridView;

import java.io.Serializable;
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

public class PhotoListActivity extends BaseActivity implements BaseAdapter.OnItemClickListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.gvPhoto)
    PullToRefreshGridView mPullRefreshGridView;
    private int mTaskId;
    //图片展示
    private PhotoGridViewAdapter mAdapter;
    private GridView mListView;
    private List<PhotoBean> iData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_list);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mTaskId = getIntent().getIntExtra("TaskId", 0);
        //图片展示
        //PullToRefreshListView-start
        mPullRefreshGridView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        //设置刷新标签
        mPullRefreshGridView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
        //设置下拉标签
        mPullRefreshGridView.getLoadingLayoutProxy().setPullLabel("准备刷新");
        //设置释放标签
        mPullRefreshGridView.getLoadingLayoutProxy().setReleaseLabel("释放开始刷新");
        mPullRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //下拉刷新 业务代码
//                    rLoad();
                } else if (refreshView.isFooterShown()) {
                    //上拉加载更多 业务代码
//                    loadData();
                } else {
                    mPullRefreshGridView.onRefreshComplete();
                }
            }
        });
        mListView = mPullRefreshGridView.getRefreshableView();
        mAdapter = new PhotoGridViewAdapter(context);
        mAdapter.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);
        showLoading();
        getPhoto();
    }

    @OnClick({R.id.ivBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }

    //获取照片
    private void getPhoto() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("TaskId", mTaskId);
        Call<ResponBean> call = server.getService().getEquipmentFileList(map);
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        showToast(response.body().getMessage());
                        List<PhotoBean> bean = new Gson().fromJson(response.body().getData(), new TypeToken<List<PhotoBean>>() {
                        }.getType());
                        iData.clear();
                        iData.addAll(bean);
                        if (iData != null && iData.size() > 0) {
                            mListView.setVisibility(View.VISIBLE);
                            mAdapter.setData(iData);
                            mAdapter.notifyDataSetChanged();
                        } else {
                            mListView.setVisibility(View.GONE);
                        }
                    } else {
                        showToast(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showToast("加载失败");
                }
                mPullRefreshGridView.onRefreshComplete();
            }

            @Override
            public void onFailure(Call<ResponBean> call, Throwable throwable) {
                if (!call.isCanceled()) {
                    mPullRefreshGridView.onRefreshComplete();
                    hideLoading();
                    showToast(parseError(throwable));
                }
            }
        });
    }

    @Override
    public void onItemClick(View v, int position) {
        Intent intent = new Intent(context, ViewPagerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) iData);
        intent.putExtras(bundle);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
