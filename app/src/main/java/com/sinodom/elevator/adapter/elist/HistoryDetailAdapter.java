package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.business.HistoryDetailBean;
import com.sinodom.elevator.bean.rescue.ComplaintBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 安卓 on 2017/11/14.
 */

public class HistoryDetailAdapter extends BaseAdapter<HistoryDetailBean> {

    private LayoutInflater inflater;

    public HistoryDetailAdapter(Context context,List<HistoryDetailBean> mList) {
        super(context, mList);
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void updateView(List<HistoryDetailBean> nowList) {
        this.data = nowList;
        this.notifyDataSetChanged();//强制动态刷新数据进而调用getView方法
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_record2, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        instantiation(holder, data.get(position));

        return convertView;
    }

    private void instantiation(ViewHolder holder, HistoryDetailBean mListBean) {

        holder.tvNumbering.setText("" + mListBean.getLiftNum());
        holder.tvAddress.setText("" + mListBean.getInstallationAddress());
//        holder.tvAddress2.setText("" + mListBean.getInstallationAddress());

        holder.tvState.setText("" + mListBean.getMaintenancePeriod());


        if (mListBean.getMaintenancePeriod().trim().equals("未维保")) {
            String s3 = "" + mListBean.getCreateTime();
            String[] temp = null;
            temp = s3.split("T");
            String s4 = "" + mListBean.getInspectionNextDate();
            String[] temp2 = null;
            temp2 = s4.split("T");
            holder.tvTime.setText("" + temp[0]);
            holder.tvTime2.setText("——" + temp2[0]);
        } else {
            String s3 = "" + mListBean.getUpdateDatetime();
            String[] temp = null;
            temp = s3.split("T");
            String strTime = temp[1].substring(0, 5);
            holder.tvTime.setText("" + temp[0]);
            holder.tvTime2.setText("" + strTime);
        }


    }

    static class ViewHolder {
        @BindView(R.id.tvNumbering)
        TextView tvNumbering;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvState)
        TextView tvState;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvTime2)
        TextView tvTime2;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
