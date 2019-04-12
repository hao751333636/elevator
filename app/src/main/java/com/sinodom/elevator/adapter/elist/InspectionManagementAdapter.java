package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.Inspection.InspectionDataBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 安卓 on 2017/12/4.
 */

public class InspectionManagementAdapter extends BaseAdapter<InspectionDataBean> {

    public InspectionManagementAdapter(Context context, List<InspectionDataBean> data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_internet_alarm, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        initView(holder, data.get(position), position);
        return convertView;
    }

    private void initView(ViewHolder holder, InspectionDataBean inspectionDataBean, int position) {

        holder.tvNumbering.setText(inspectionDataBean.getLiftNum());
        holder.tvState.setText(inspectionDataBean.getMaintenancePeriod());
        holder.tvState1.setText("检验状态：");
        holder.tvAddress.setText(inspectionDataBean.getInstallationAddress());
    }

//    public void setData(List<InspectionDataBean> data) {
//        if (data == null) {
//            data = new ArrayList<>();
//        }
//        this.data = data;
//    }

    class ViewHolder {
        @BindView(R.id.tvNumbering)
        TextView tvNumbering;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvState1)
        TextView tvState1;
        @BindView(R.id.tvState)
        TextView tvState;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvTime2)
        TextView tvTime2;
        @BindView(R.id.llTime)
        LinearLayout llTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
