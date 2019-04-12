package com.sinodom.elevator.fragment.elevator.rescue;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sinodom.elevator.R;
import com.sinodom.elevator.fragment.BaseFragment;
import com.sinodom.elevator.fragment.elevator.rescue.rescuelist.AlreadyFragment;
import com.sinodom.elevator.fragment.elevator.rescue.rescuelist.NotYetFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 安卓 on 2017/11/14.
 * 电梯救援-救援列表
 */

public class RescueFragment extends BaseFragment {

    @BindView(R.id.tvNotYet)
    TextView tvNotYet;
    @BindView(R.id.tvAlready)
    TextView tvAlready;
    Unbinder unbinder;
    @BindView(R.id.tvNotYetLine)
    TextView tvNotYetLine;
    @BindView(R.id.llNotYet)
    LinearLayout llNotYet;
    @BindView(R.id.tvAlreadyLine)
    TextView tvAlreadyLine;
    @BindView(R.id.llAlready)
    LinearLayout llAlready;
    private NotYetFragment mNotYetFragment = null;
    private AlreadyFragment mAlreadyFragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rescue_rescue, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            if (CallFragment.isRefresh) {
                CallFragment.isRefresh = false;
                if (mNotYetFragment.isStart && !mNotYetFragment.isRefresh) {
                    mNotYetFragment.showLoading("加载中...");
                    mNotYetFragment.rLoad();
                }
                if (mAlreadyFragment.isStart && !mAlreadyFragment.isRefresh) {
                    mAlreadyFragment.showLoading("加载中...");
                    mAlreadyFragment.rLoad();
                }
            }
            if (OrderFragment.isRefresh) {
                OrderFragment.isRefresh = false;
                if (mNotYetFragment.isStart && !mNotYetFragment.isRefresh) {
                    mNotYetFragment.showLoading("加载中...");
                    mNotYetFragment.rLoad();
                }
                if (mAlreadyFragment.isStart && !mAlreadyFragment.isRefresh) {
                    mAlreadyFragment.showLoading("加载中...");
                    mAlreadyFragment.rLoad();
                }
            }
        }
        super.onHiddenChanged(hidden);
        Logger.e("onHiddenChanged" + hidden);
    }

    private void init() {
        mNotYetFragment = new NotYetFragment();
        mAlreadyFragment = new AlreadyFragment();
        getChildFragmentManager().beginTransaction().add(R.id.tabPager, mNotYetFragment).show(mNotYetFragment).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.llNotYet, R.id.llAlready})
    public void onViewClicked(View view) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.llNotYet:
                resetTabBtn();
                tvNotYet.setTextColor(getResources().getColor(R.color.actionbar));
                tvNotYetLine.setVisibility(View.VISIBLE);
                hideFragments(ft);
                if (mNotYetFragment == null) {
                    mNotYetFragment = new NotYetFragment();
                }
                if (!mNotYetFragment.isAdded()) {
                    ft.add(R.id.tabPager, mNotYetFragment);
                }
                ft.show(mNotYetFragment).commit();
                break;
            case R.id.llAlready:
                resetTabBtn();
                tvAlready.setTextColor(getResources().getColor(R.color.actionbar));
                tvAlreadyLine.setVisibility(View.VISIBLE);
                hideFragments(ft);
                if (mAlreadyFragment == null) {
                    mAlreadyFragment = new AlreadyFragment();
                }
                if (!mAlreadyFragment.isAdded()) {
                    ft.add(R.id.tabPager, mAlreadyFragment);
                }
                ft.show(mAlreadyFragment).commit();
                break;
        }
    }

    public void setFragment() {
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mNotYetFragment != null) {
            transaction.hide(mNotYetFragment);
        }
        if (mAlreadyFragment != null) {
            transaction.hide(mAlreadyFragment);
        }
    }

    protected void resetTabBtn() {
        tvNotYet.setTextColor(getResources().getColor(R.color.black1));
        tvAlready.setTextColor(getResources().getColor(R.color.black1));
        tvNotYetLine.setVisibility(View.GONE);
        tvAlreadyLine.setVisibility(View.GONE);
    }
}
