package com.sinodom.elevator.fragment.inspect.inspect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.elist.RecordDisplayListAdapter;
import com.sinodom.elevator.bean.elevator.ExamineDataBean;
import com.sinodom.elevator.bean.steplist.StepDataBean;
import com.sinodom.elevator.fragment.BaseFragment;
import com.sinodom.elevator.view.NoScrollListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 安卓 on 2017/11/28.
 * 检验历史
 */

public class RecordDisplayFragment extends BaseFragment {

    @BindView(R.id.lvInspection)
    NoScrollListView lvInspection;
    Unbinder unbinder;
    private List<ExamineDataBean> mDataBeen = null;
    private List<StepDataBean.InspectDetailsBean> mDataBeenValue = null;
    private RecordDisplayListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inspection, null);
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        mDataBeen = (List<ExamineDataBean>) bundle.getSerializable("list");
        mDataBeenValue = (List<StepDataBean.InspectDetailsBean>) bundle.getSerializable("list2");

        adapter = new RecordDisplayListAdapter(getActivity(), mDataBeen, mDataBeenValue);
        lvInspection.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
