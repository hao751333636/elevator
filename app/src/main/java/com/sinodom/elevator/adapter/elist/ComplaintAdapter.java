package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.rescue.ComplaintBean;
import com.sinodom.elevator.util.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 投诉建议列表
 */
public class ComplaintAdapter extends BaseAdapter<ComplaintBean> {

    public ComplaintAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_complaint_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final ComplaintBean bean = data.get(i);
        holder.tvType.setText(bean.getAdviceTypeDict().getDictName());
        holder.tvContent.setText(bean.getTitle());
        holder.tvCreateTime.setText(DateUtil.format(bean.getCreateTime()));
        if(bean.getAdviceStatusDict().getDictName().equals("已完成")){
            holder.ivIcon.setBackgroundResource(R.mipmap.ic_complaint_complete);
        }else if(bean.getAdviceStatusDict().getDictName().equals("待跟进")){
            holder.ivIcon.setBackgroundResource(R.mipmap.ic_complaint_wait);
        }else if(bean.getAdviceStatusDict().getDictName().equals("跟进中")){
            holder.ivIcon.setBackgroundResource(R.mipmap.ic_complaint_followup);
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
        @BindView(R.id.tvType)
        TextView tvType;
        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.tvCreateTime)
        TextView tvCreateTime;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}