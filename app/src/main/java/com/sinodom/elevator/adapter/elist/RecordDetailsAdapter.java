package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.business.RecordDetailsBean;
import com.sinodom.elevator.util.glide.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 安卓 on 2017/12/18.
 * 电梯业务 维保记录详情Adapter
 */

public class RecordDetailsAdapter extends BaseAdapter<RecordDetailsBean.CheckDetailsBean> {
    private String name;

    public RecordDetailsAdapter(Context context, List<RecordDetailsBean.CheckDetailsBean> mList, String name) {
        super(context, mList);
        this.name = name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_record_details, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        instantiation(holder, data.get(position));
        return convertView;
    }

    private void instantiation(ViewHolder holder, RecordDetailsBean.CheckDetailsBean checkDetailsBean) {

        holder.tvContent.setText("" + checkDetailsBean.getStep().getStepName().trim());
        if (checkDetailsBean.isIsPassed()) {
            holder.tvResults.setText("合格");
        } else {
            holder.tvResults.setText("不合格");
        }

        holder.tvName.setText("" + name);
        if (checkDetailsBean.getRemark() != null && !checkDetailsBean.getRemark().equals("")) {
            holder.tvRemark.setText("备注:" + checkDetailsBean.getRemark());
        } else {
            holder.tvRemark.setText("备注:");
        }
        if (checkDetailsBean.getCheckDate() != null) {
            String s3 = "" + checkDetailsBean.getCheckDate();
            String[] temp = null;
            temp = s3.split("T");
            String strTime = temp[1].substring(0, 5);
            holder.tvTime1.setText("" + temp[0]);
            holder.tvTime2.setText("" + strTime);
        }

        if (checkDetailsBean.getPhotoUrl() != null && !checkDetailsBean.getPhotoUrl().equals("")) {
            String str = checkDetailsBean.getPhotoUrl().substring(2);
            String myUrl = BuildConfig.SERVER + str;
            Logger.d("myUrl=" + myUrl);
            GlideApp.with(context)
                    .load(myUrl)
                    .thumbnail(0.1f)
                    .error(R.mipmap.ic_failure)
                    .placeholder(R.mipmap.ic_load)
                    .into(holder.ivPicture);
        } else {
            holder.ivPicture.setImageResource(R.mipmap.ic_no_photos);
        }
    }

    class ViewHolder {
        @BindView(R.id.ivPicture)
        ImageView ivPicture;
        @BindView(R.id.tv1)
        TextView tv1;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.tvResults)
        TextView tvResults;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvTime1)
        TextView tvTime1;
        @BindView(R.id.tvTime2)
        TextView tvTime2;
        @BindView(R.id.tvRemark)
        TextView tvRemark;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
