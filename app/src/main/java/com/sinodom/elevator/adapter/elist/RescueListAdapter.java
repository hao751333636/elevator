package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.rescue.RescueBean;
import com.sinodom.elevator.util.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 救援列表
 */
public class RescueListAdapter extends BaseAdapter<RescueBean> {

    public RescueListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_rescue_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final RescueBean bean = data.get(i);
        holder.tvLiftNum.setText(bean.getLift() != null ? bean.getLift().getLiftNum() : "");
        holder.tvStatusName.setText(bean.getStatusName());
        holder.tvInstallationAddress.setText(bean.getLift() != null ? bean.getLift().getInstallationAddress() : "");
        holder.tvTotalLossTime.setText(bean.getTotalLossTime() + "分钟");
        holder.tvCreateTime.setText(DateUtil.format(bean.getCreateTime()));
        holder.ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    try {
                        onClickListener.onClick(v, i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    try {
                        onItemClickListener.onItemClick(v, i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tvLiftNum)
        TextView tvLiftNum;
        @BindView(R.id.tvStatusName)
        TextView tvStatusName;
        @BindView(R.id.tvInstallationAddress)
        TextView tvInstallationAddress;
        @BindView(R.id.tvTotalLossTime)
        TextView tvTotalLossTime;
        @BindView(R.id.tvCreateTime)
        TextView tvCreateTime;
        @BindView(R.id.ivImg)
        ImageView ivImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}