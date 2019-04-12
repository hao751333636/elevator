package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.repairrecord.RepairRecordBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 安卓 on 2017/12/4.
 */

public class RepairRecordAdapter extends BaseAdapter<RepairRecordBean> {

    private boolean display;

    public RepairRecordAdapter(Context context, List<RepairRecordBean> mList, boolean display) {
        super(context, mList);
        this.display = display;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_repair_record, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        initView(holder, data.get(position));
        return convertView;
    }

    private void initView(ViewHolder holder, RepairRecordBean bean) {

        holder.tvNumbering.setText(bean.getLiftNum());
        holder.tvAddress.setText(bean.getInstallationAddress());

        if (display) {
            if (bean.getCreateTime() != null && !bean.getCreateTime().equals("")) {
                holder.llTime.setVisibility(View.VISIBLE);
                String s3 = "" + bean.getCreateTime();
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
    class ViewHolder {
        @BindView(R.id.tvNumbering)
        TextView tvNumbering;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
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
