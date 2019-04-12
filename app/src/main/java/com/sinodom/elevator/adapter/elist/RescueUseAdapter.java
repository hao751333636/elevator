package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.rescue.RescueDetailBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 救援使用人员信息列表
 */
public class RescueUseAdapter extends BaseAdapter<RescueDetailBean.LiftBean.UseUsersBean> {

    public RescueUseAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_rescue_user_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final RescueDetailBean.LiftBean.UseUsersBean bean = data.get(i);
        holder.tvName.setText(bean.getUserName());
        holder.tvPhone.setText(bean.getMobile());
        view.setOnClickListener(new View.OnClickListener() {
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
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPhone)
        TextView tvPhone;
        @BindView(R.id.ivPhone)
        ImageView ivPhone;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}