package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.business.EquipmentDataBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 安卓 on 2017/12/4.
 * 维保签字
 */

public class SignAdapter extends BaseAdapter<EquipmentDataBean> {

    private String source;
    private boolean display;


    public SignAdapter(Context context, List<EquipmentDataBean> mList, String source, boolean display) {
        super(context, mList);
        this.source = source;
        this.display = display;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_sign, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        initView(holder, data.get(position));
        return convertView;
    }

    private void initView(ViewHolder holder, EquipmentDataBean equipmentDataBean) {
        holder.tvNumbering.setText(equipmentDataBean.getLiftNum());
        String CType = equipmentDataBean.getCType();
        if (CType == null) {
            CType = "5";
        }
        String type = "";
        switch (CType) {
            case "0":
                type = "半月维保";
                break;
            case "2":
                type = "季度维保";
                break;
            case "3":
                type = "半年维保";
                break;
            case "4":
                type = "年度维保";
                break;
        }
        if (equipmentDataBean.isIsPassed()) {
            holder.tvState.setText("合格(" + type + ")");
        } else {
            holder.tvState.setText("不合格(" + type + ")");
        }
        holder.tvState1.setText(source);
        holder.tvAddress.setText(equipmentDataBean.getInstallationAddress());

        if (display) {
            if (equipmentDataBean.getCheckDate() != null && !equipmentDataBean.getCheckDate().equals("")) {
                holder.llTime.setVisibility(View.VISIBLE);
                String s3 = "" + equipmentDataBean.getCheckDate();
                String[] temp = null;
                temp = s3.split("T");
                holder.tvTime.setText("" + temp[0]);

                String strTime = temp[1].substring(0, 5);

                holder.tvTime2.setText("" + strTime);
            }
        } else {
            holder.llTime.setVisibility(View.GONE);
        }
    }

    static class ViewHolder {
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
