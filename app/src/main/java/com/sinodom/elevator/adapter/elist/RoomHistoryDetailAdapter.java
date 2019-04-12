package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.nim.RoomHistoryDetailBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 投诉建议列表
 */
public class RoomHistoryDetailAdapter extends BaseAdapter<RoomHistoryDetailBean> {

    public RoomHistoryDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_room_history_detail, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final RoomHistoryDetailBean bean = data.get(i);
        holder.tvPhone.setText(bean.getPhone());
        holder.tvName.setText(bean.getUserName());
        holder.tvType.setText("（" + bean.getRoleName() + ")");
        if (bean.getState() == 1) {
            holder.tvState.setText("已接听");
            holder.tvState.setTextColor(context.getResources().getColor(R.color.actionbar));
        } else {
            holder.tvState.setText("未接听");
            holder.tvState.setTextColor(context.getResources().getColor(R.color.red));
        }
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
        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.tvPhone)
        TextView tvPhone;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvType)
        TextView tvType;
        @BindView(R.id.tvState)
        TextView tvState;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}