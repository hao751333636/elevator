package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.sinodom.elevator.BuildConfig;
import com.sinodom.elevator.R;
import com.sinodom.elevator.activity.inspect.video.VideoPlayerActivity;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.elevator.ExamineDataBean;
import com.sinodom.elevator.bean.steplist.StepDataBean;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.view.SeeImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 安卓 on 2017/11/28.
 */

public class RecordDisplayListAdapter extends BaseAdapter<ExamineDataBean> {
    private List<StepDataBean.InspectDetailsBean> mDataBeenValue;

    public RecordDisplayListAdapter(Context context, List<ExamineDataBean> mList, List<StepDataBean.InspectDetailsBean> mDataBeenValue) {
        super(context, mList);
        this.mDataBeenValue = mDataBeenValue;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_record_display_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(data.get(position).getID() + "  、" + data.get(position).getStepName());
        for (int b = 0; b < mDataBeenValue.size(); b++) {
            if (data.get(position).getID() == mDataBeenValue.get(b).getStepId()) {
                if (mDataBeenValue.get(b).isIsPassed()) {
                    holder.tvResults.setText("合格");
                    holder.tvResults.setTextColor(context.getResources().getColor(R.color.blue_text_color));
                } else {
                    holder.tvResults.setText("不合格");
                    holder.tvResults.setTextColor(context.getResources().getColor(R.color.btn_red));
                }
                if (!TextUtil.isEmpty(mDataBeenValue.get(b).getRemark())) {
                    holder.tvOpinion.setText("检验意见：" + mDataBeenValue.get(b).getRemark());
                } else {
                    holder.tvOpinion.setText("检验意见：暂无");
                }
                if (!TextUtil.isEmpty(mDataBeenValue.get(b).getCreateTime())) {
                    String s3 = "" + mDataBeenValue.get(b).getCreateTime();
                    String[] temp = null;
                    temp = s3.split("T");
                    String strTime = temp[1].substring(0, 5);
                    holder.tvTime.setText("检验时间：" + temp[0] + "  " + strTime);
                }
                if (mDataBeenValue.get(b).getUser() != null && !TextUtil.isEmpty(mDataBeenValue.get(b).getUser().getUserName())) {
                    holder.tvName.setText("检  验  人：" + mDataBeenValue.get(b).getUser().getUserName());
                }
                final int a = b;
                holder.llDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mDataBeenValue.get(a).getPhotoUrl() != null && !mDataBeenValue.get(a).getPhotoUrl().equals("")) {
                            SeeImageView seeImageView = new SeeImageView(context, R.style.dialog, mDataBeenValue.get(a).getPhotoUrl(), false);
                            seeImageView.show();
                        } else if (mDataBeenValue.get(a).getVideoPath() != null && !mDataBeenValue.get(a).getVideoPath().equals("")) {
                            String str = mDataBeenValue.get(a).getVideoPath().substring(2);
                            Logger.d("url截取= " + str);
                            String myUrl = BuildConfig.SERVER + str;

                            context.startActivity(new Intent(context, VideoPlayerActivity.class).putExtra(
                                    "path", myUrl));
                        }
                    }
                });

                break;
            }

        }


        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvResults)
        TextView tvResults;
        @BindView(R.id.tvOpinion)
        TextView tvOpinion;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.llDetails)
        LinearLayout llDetails;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
