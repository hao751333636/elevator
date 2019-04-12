package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.property.PropertyBean;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 安卓 on 2017/11/2.
 * 物业巡查
 */

public class PropertyAdapter extends BaseAdapter<PropertyBean.PropertyStepBean> {

    public PropertyAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_elevator_survey, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final PropertyBean.PropertyStepBean bean = data.get(i);
        holder.mItemEleListTv.setText(i + 1 + "  、" + bean.getStepName());
        holder.ivQualified.setImageResource(R.mipmap.qualified);
        holder.ivUnqualified.setImageResource(R.mipmap.qualified);
        holder.llQualified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入照相
                holder.ivQualified.setImageResource(R.mipmap.unqualified);
                holder.ivUnqualified.setImageResource(R.mipmap.qualified);
                bean.setIsPassed(true);
                bean.setIsSelect(true);
                if (onClickListener != null) {
                    onClickListener.onClick(v, i);
                }

            }
        });
        holder.llUnqualified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入照相
                holder.ivQualified.setImageResource(R.mipmap.qualified);
                holder.ivUnqualified.setImageResource(R.mipmap.unqualified);
                bean.setIsPassed(false);
                bean.setIsSelect(true);
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, i);
                }
            }
        });

        removeTextWatcher(holder.mItemEleListEt);
        TextWatcher watcher = new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s)) {
                    bean.setRemark(String.valueOf(s));
                }
            }
        };
        holder.mItemEleListEt.addTextChangedListener(watcher);
        holder.mItemEleListEt.setTag(watcher);
        holder.mItemEleListEt.setText(bean.getRemark());
        if (bean.isIsTakePhoto()) {
            holder.ivPictures.setVisibility(View.VISIBLE);
        } else {
            holder.ivPictures.setVisibility(View.GONE);
        }

        return convertView;
    }


    private void removeTextWatcher(EditText editText) {
        if (editText.getTag() instanceof TextWatcher) {
            editText.removeTextChangedListener((TextWatcher) editText.getTag());
        }
    }

    static class ViewHolder {
        @BindView(R.id.mItemEleListTv)
        TextView mItemEleListTv;
        @BindView(R.id.ivQualified)
        ImageView ivQualified;
        @BindView(R.id.tvQualified)
        TextView tvQualified;
        @BindView(R.id.llQualified)
        LinearLayout llQualified;
        @BindView(R.id.ivUnqualified)
        ImageView ivUnqualified;
        @BindView(R.id.llUnqualified)
        LinearLayout llUnqualified;
        @BindView(R.id.ivPictures)
        ImageView ivPictures;
        @BindView(R.id.mItemEleListEt)
        EditText mItemEleListEt;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
