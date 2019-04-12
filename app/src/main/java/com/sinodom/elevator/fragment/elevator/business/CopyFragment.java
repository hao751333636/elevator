package com.sinodom.elevator.fragment.elevator.business;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinodom.elevator.R;
import com.sinodom.elevator.fragment.BaseFragment;

/**
 * Created by 安卓 on 2017/11/14.
 */

public class CopyFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inspection, null);
        initView(view);
        init();
        return view;
    }

    private void initView(View v) {
    }

    private void init() {
    }
}
