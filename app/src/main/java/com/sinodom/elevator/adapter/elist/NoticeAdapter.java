package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.notice.NoticeBean;
import com.sinodom.elevator.util.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 通知通告
 */
public class NoticeAdapter extends BaseAdapter<NoticeBean> {

    public NoticeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_notice_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final NoticeBean bean = data.get(i);
        holder.tvType.setText(bean.getNoticeTypeName().getDictName().trim());
        holder.tvTitle.setText(bean.getTitle());
        holder.tvCreateTime.setText(DateUtil.format(bean.getCreateTime()));
        if (bean.getNoticeTypeName().getDictName().equals("通知 ")) {
            holder.ivIcon.setBackgroundResource(R.mipmap.ic_notice_notice);
        } else {
            holder.ivIcon.setBackgroundResource(R.mipmap.ic_notice_proclamation);
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
        @BindView(R.id.tvType)
        TextView tvType;
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvCreateTime)
        TextView tvCreateTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}