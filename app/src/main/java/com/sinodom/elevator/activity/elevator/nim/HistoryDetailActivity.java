package com.sinodom.elevator.activity.elevator.nim;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.adapter.elist.RoomHistoryDetailAdapter;
import com.sinodom.elevator.bean.ResponBean;
import com.sinodom.elevator.bean.nim.RoomHistoryBean;
import com.sinodom.elevator.bean.nim.RoomHistoryDetailBean;
import com.sinodom.elevator.util.DateUtil;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.view.NoScrollListView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryDetailActivity extends BaseActivity {

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.lvListView)
    NoScrollListView lvListView;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvTime1)
    TextView tvTime1;
    @BindView(R.id.tvState)
    TextView tvState;
    private RoomHistoryDetailAdapter mAdapter;
    private List<RoomHistoryDetailBean> mList = new ArrayList<>();
    private Gson gson = new Gson();
    private int mPageIndex = 1;
    private RoomHistoryBean mBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        ButterKnife.bind(this);
        mBean = (RoomHistoryBean) getIntent().getSerializableExtra("bean");
        tvName.setText(mBean.getUserName());
        tvPhone.setText(mBean.getPhone());
        tvType.setText(mBean.getRoleName());
        tvTime.setText(mBean.getCreateTime());
        try {
            if (!TextUtil.isEmpty(mBean.getEndTime())) {
                long startTime = DateUtil.stringToLong(mBean.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
                long endTime = DateUtil.stringToLong(mBean.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                tvTime1.setText(DateUtil.secondToTime((endTime - startTime) / 1000));
            } else {
                tvTime1.setText("未接听");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (mBean.getState() == 0) {
            tvState.setText("呼出视频");
        } else if (mBean.getState() == 1) {
            tvState.setText("呼入视频");
        } else if (mBean.getState() == 2) {
            tvState.setText("未接听");

        }
        mAdapter = new RoomHistoryDetailAdapter(context);
        lvListView.setAdapter(mAdapter);
        loadData();
    }

    private void loadData() {
        Call<ResponBean> call = server.getService().getAppAlarmRoomRecordUser(mBean.getRoomID());
        mRetrofitManager.call(call, new Callback<ResponBean>() {
            @Override
            public void onResponse(Call<ResponBean> call, Response<ResponBean> response) {
                hideLoading();
                try {
                    if (response.body().isSuccess()) {
                        Logger.json(response.body().getData());
                        List<RoomHistoryDetailBean> list = gson.fromJson(response.body().getData(), new TypeToken<List<RoomHistoryDetailBean>>() {
                        }.getType());
                        mPageIndex = mPageIndex + 1;
                        for (RoomHistoryDetailBean bean : list) {
                            if (bean.getState() == 0) {
                                list.remove(bean);
                            }
                        }
                        mList.addAll(list);
                        mAdapter.setData(mList);
                        mAdapter.notifyDataSetChanged();
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        finish();
    }
}
