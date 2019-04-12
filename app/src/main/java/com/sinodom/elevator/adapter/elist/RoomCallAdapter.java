package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.nim.RoomCallBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RoomCallAdapter extends BaseAdapter<RoomCallBean> {

    public RoomCallAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_room_call, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final RoomCallBean bean = data.get(i);
        holder.tvPhone.setText(bean.getMobile());
        holder.tvName.setText(bean.getUserName());
        holder.tvType.setText("（" + bean.getRoleName() + "）");
        holder.checkBox.setChecked(bean.isSelect());
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
        CircleImageView ivIcon;
        @BindView(R.id.tvPhone)
        TextView tvPhone;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvType)
        TextView tvType;
        @BindView(R.id.checkBox)
        CheckBox checkBox;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}