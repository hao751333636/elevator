package com.sinodom.elevator.activity.inspect;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.BaseActivity;
import com.sinodom.elevator.fragment.elevator.main.MyFragment;
import com.sinodom.elevator.fragment.inspect.main.InspectFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 电梯检验--主页
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tabPager)
    LinearLayout tabPager;
    @BindView(R.id.rlMaintenance)
    RelativeLayout rlMaintenance;
    @BindView(R.id.rlMy)
    RelativeLayout rlMy;
    @BindView(R.id.ivMaintenance)
    ImageView ivMaintenance;
    @BindView(R.id.tvMaintenance)
    TextView tvMaintenance;
    @BindView(R.id.ivMy)
    ImageView ivMy;
    @BindView(R.id.tvMy)
    TextView tvMy;

    private InspectFragment inspectFragment = null;
    private MyFragment myFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        inspectFragment = new InspectFragment();
        myFragment = new MyFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.tabPager, inspectFragment).show(inspectFragment).commit();
    }


    @OnClick({R.id.rlMaintenance, R.id.rlMy})
    public void onViewClicked(View view) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.rlMaintenance:
                resetTabBtn();
                tvMaintenance.setTextColor(ContextCompat.getColor(context, R.color.actionbar));
                ivMaintenance.setBackgroundResource(R.mipmap.ic_tab_help_sel);
                hideFragments(ft);
                if (inspectFragment == null) {
                    inspectFragment = new InspectFragment();
                }
                if (!inspectFragment.isAdded()) {
                    ft.add(R.id.tabPager, inspectFragment);
                }
                ft.show(inspectFragment).commit();
                break;
            case R.id.rlMy:
                resetTabBtn();
                tvMy.setTextColor(ContextCompat.getColor(context, R.color.actionbar));
                ivMy.setBackgroundResource(R.mipmap.ic_tab_me_sel);
                hideFragments(ft);
                if (myFragment == null) {
                    myFragment = new MyFragment();
                }
                if (!myFragment.isAdded()) {
                    ft.add(R.id.tabPager, myFragment);
                }
                ft.show(myFragment).commit();
                break;
        }
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (inspectFragment != null) {
            transaction.hide(inspectFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
    }

    protected void resetTabBtn() {
        tvMaintenance.setTextColor(ContextCompat.getColor(context, R.color.tab_gray));
        tvMy.setTextColor(ContextCompat.getColor(context, R.color.tab_gray));
        ivMaintenance.setBackgroundResource(R.mipmap.ic_tab_help);
        ivMy.setBackgroundResource(R.mipmap.ic_tab_me);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return doubleBackToExit(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    //fragment切换失效问题
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }
}
