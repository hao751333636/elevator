package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.parts.PartsBean;
import com.sinodom.elevator.util.DateUtil;
import com.sinodom.elevator.util.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 配件管理列表
 */
public class PartsAdapter extends BaseAdapter<PartsBean.ListBean> {

    public PartsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_parts, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final PartsBean.ListBean bean = data.get(i);
        holder.tvName.setText(bean.getProductName());
        holder.tvNum.setText(bean.getBrand());
        holder.tvCode.setText("型号：" + bean.getModel());
        holder.tvTime.setText(TextUtil.isEmpty(bean.getInstallationTime()) ? "" : "安装时间：" + DateUtil.format(bean.getInstallationTime()));
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
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    try {
                        onItemLongClickListener.onItemLongClick(v, i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.tvLabel)
        TextView tvLabel;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvNum)
        TextView tvNum;
        @BindView(R.id.tvCode)
        TextView tvCode;
        @BindView(R.id.tvTime)
        TextView tvTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}