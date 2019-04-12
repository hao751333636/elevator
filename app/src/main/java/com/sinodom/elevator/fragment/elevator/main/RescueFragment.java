package com.sinodom.elevator.fragment.elevator.main;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.fragment.BaseFragment;
import com.sinodom.elevator.fragment.elevator.rescue.CallFragment;
import com.sinodom.elevator.fragment.elevator.rescue.ComplaintFragment;
import com.sinodom.elevator.fragment.elevator.rescue.OrderFragment;
import com.sinodom.elevator.fragment.elevator.rescue.RecordFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 安卓 on 2017/11/14.
 * 电梯维保
 */

public class RescueFragment extends BaseFragment {

    @BindView(R.id.tvCall)
    TextView tvCall;
    @BindView(R.id.tvRescue)
    TextView tvRescue;
    @BindView(R.id.tvRecord)
    TextView tvRecord;
    @BindView(R.id.tvOrder)
    TextView tvOrder;
    @BindView(R.id.tvComplaint)
    TextView tvComplaint;
    Unbinder unbinder;
    @BindView(R.id.llCall)
    LinearLayout llCall;
    @BindView(R.id.llRescue)
    LinearLayout llRescue;
    @BindView(R.id.llRecord)
    LinearLayout llRecord;
    @BindView(R.id.llOrder)
    LinearLayout llOrder;
    @BindView(R.id.llComplaint)
    LinearLayout llComplaint;
    @BindView(R.id.tabPager)
    LinearLayout tabPager;
    private CallFragment mCallFragment = null;
    private com.sinodom.elevator.fragment.elevator.rescue.RescueFragment mRescueFragment = null;
    private RecordFragment mRecordFragment = null;
    private OrderFragment mOrderFragment = null;
    private ComplaintFragment mComplaintFragment = null;
    private int mId = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rescue, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mCallFragment = new CallFragment();
        mRescueFragment = new com.sinodom.elevator.fragment.elevator.rescue.RescueFragment();
        mRecordFragment = new RecordFragment();
        mOrderFragment = new OrderFragment();
//        mComplaintFragment = new ComplaintFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", 0);
        mCallFragment.setArguments(bundle);
        getChildFragmentManager().beginTransaction().add(R.id.tabPager, mCallFragment).show(mCallFragment).commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setCall() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        resetTabBtn();
        tvCall.setBackgroundResource(R.drawable.btn_white_normal);
        tvCall.setTextColor(getResources().getColor(R.color.actionbar));
        hideFragments(ft);
        if (mCallFragment == null) {
            mCallFragment = new CallFragment();
        }
        if (!mCallFragment.isAdded()) {
            ft.add(R.id.tabPager, mCallFragment);
        }
        try {

            ft.show(mCallFragment).commit();
        }catch (Exception e){}
    }

    @OnClick({R.id.llCall, R.id.llRescue, R.id.llRecord, R.id.llOrder, R.id.llComplaint})
    public void onViewClicked(View view) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.llCall:
                resetTabBtn();
                tvCall.setBackgroundResource(R.drawable.btn_white_normal);
                tvCall.setTextColor(getResources().getColor(R.color.actionbar));
                hideFragments(ft);
                if (mCallFragment == null) {
                    mCallFragment = new CallFragment();
                }
                if (!mCallFragment.isAdded()) {
                    ft.add(R.id.tabPager, mCallFragment);
                }
                ft.show(mCallFragment).commit();
                break;
            case R.id.llRescue:
                resetTabBtn();
                tvRescue.setBackgroundResource(R.drawable.btn_white_normal);
                tvRescue.setTextColor(getResources().getColor(R.color.actionbar));
                hideFragments(ft);
                if (mRescueFragment == null) {
                    mRescueFragment = new com.sinodom.elevator.fragment.elevator.rescue.RescueFragment();
                }
                if (!mRescueFragment.isAdded()) {
                    ft.add(R.id.tabPager, mRescueFragment);
                }
                ft.show(mRescueFragment).commit();
                break;
            case R.id.llRecord:
                resetTabBtn();
                tvRecord.setBackgroundResource(R.drawable.btn_white_normal);
                tvRecord.setTextColor(getResources().getColor(R.color.actionbar));
                hideFragments(ft);
                if (mRecordFragment == null) {
                    mRecordFragment = new RecordFragment();
                }
                if (!mRecordFragment.isAdded()) {
                    ft.add(R.id.tabPager, mRecordFragment);
                }
                ft.show(mRecordFragment).commit();
                break;
            case R.id.llOrder:
                resetTabBtn();
                tvOrder.setBackgroundResource(R.drawable.btn_white_normal);
                tvOrder.setTextColor(getResources().getColor(R.color.actionbar));
                hideFragments(ft);
                if (mOrderFragment == null) {
                    mOrderFragment = new OrderFragment();
                }
                if (!mOrderFragment.isAdded()) {
                    ft.add(R.id.tabPager, mOrderFragment);
                }
                ft.show(mOrderFragment).commit();
                break;
//            case R.id.llComplaint:
//                resetTabBtn();
//                tvComplaint.setBackgroundResource(R.drawable.btn_white_normal);
//                tvComplaint.setTextColor(getResources().getColor(R.color.actionbar));
//                hideFragments(ft);
//                if (mComplaintFragment == null) {
//                    mComplaintFragment = new ComplaintFragment();
//                }
//                if (!mComplaintFragment.isAdded()) {
//                    ft.add(R.id.tabPager, mComplaintFragment);
//                }
//                ft.show(mComplaintFragment).commit();
//                break;
        }
    }

    public void setFragment(int id) {
        mId = id;
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        resetTabBtn();
        tvCall.setBackgroundResource(R.drawable.btn_white_normal);
        tvCall.setTextColor(getResources().getColor(R.color.actionbar));
        hideFragments(ft);
        if (mCallFragment == null) {
            mCallFragment = new CallFragment();
        }
        if (!mCallFragment.isAdded()) {
            ft.add(R.id.tabPager, mCallFragment);
        }
        ft.show(mCallFragment).commit();
    }

    public int getIds() {
        int id = mId;
        mId = 0;
        return id;
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mCallFragment != null) {
            transaction.hide(mCallFragment);
        }
        if (mRescueFragment != null) {
            transaction.hide(mRescueFragment);
        }
        if (mRecordFragment != null) {
            transaction.hide(mRecordFragment);
        }
        if (mOrderFragment != null) {
            transaction.hide(mOrderFragment);
        }
//        if (mComplaintFragment != null) {
//            transaction.hide(mComplaintFragment);
//        }
    }

    protected void resetTabBtn() {
        tvCall.setBackgroundResource(R.color.transparent);
        tvRescue.setBackgroundResource(R.color.transparent);
        tvRecord.setBackgroundResource(R.color.transparent);
        tvOrder.setBackgroundResource(R.color.transparent);
//        tvComplaint.setBackgroundResource(R.color.transparent);
        tvCall.setTextColor(getResources().getColor(R.color.white));
        tvRescue.setTextColor(getResources().getColor(R.color.white));
        tvRecord.setTextColor(getResources().getColor(R.color.white));
        tvOrder.setTextColor(getResources().getColor(R.color.white));
//        tvComplaint.setTextColor(getResources().getColor(R.color.white));
    }
}
