package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.nim.RoomHistoryBean;
import com.sinodom.elevator.util.DateUtil;
import com.sinodom.elevator.util.TextUtil;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 投诉建议列表
 */
public class RoomHistoryAdapter extends BaseAdapter<RoomHistoryBean> {

    public RoomHistoryAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_room_history, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final RoomHistoryBean bean = data.get(i);
        if (bean.getState() == 0) {
            holder.ivIcon.setBackgroundResource(R.mipmap.ic_room_history_out);
        } else if (bean.getState() == 1) {
            holder.ivIcon.setBackgroundResource(R.mipmap.ic_room_history_in);
        } else if (bean.getState() == 2) {
            holder.ivIcon.setBackgroundResource(R.mipmap.ic_room_history_x);

        }
        holder.tvPhone.setText(bean.getFromPhone());
        holder.tvName.setText(bean.getFromUserName());
        holder.tvType.setText("(" + bean.getFromRoleName() + "）");
        try {
            if (!TextUtil.isEmpty(bean.getEndTime())) {
                long startTime = DateUtil.stringToLong(bean.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
                long endTime = DateUtil.stringToLong(bean.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                holder.tvTime.setText(DateUtil.secondToTime((endTime - startTime) / 1000));
            } else {
                holder.tvTime.setText("未接听");
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
        @BindView(R.id.tvTime)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}