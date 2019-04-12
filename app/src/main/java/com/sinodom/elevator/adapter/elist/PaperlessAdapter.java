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
import com.sinodom.elevator.bean.nfc.StepBean;
import com.sinodom.elevator.util.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 安卓 on 2017/11/2.
 * 无纸化电梯维保
 */

public class PaperlessAdapter extends BaseAdapter<StepBean> {

    public PaperlessAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_paperiess, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        StepBean bean = data.get(position);
        if (bean.getSelectType() == 0) {
            holder.ivQualified.setImageResource(R.mipmap.qualified);
            holder.ivUnqualified.setImageResource(R.mipmap.qualified);
        } else if (bean.getSelectType() == 1) {
            holder.ivQualified.setImageResource(R.mipmap.unqualified);
            holder.ivUnqualified.setImageResource(R.mipmap.qualified);
        } else if (bean.getSelectType() == 2) {
            holder.ivQualified.setImageResource(R.mipmap.qualified);
            holder.ivUnqualified.setImageResource(R.mipmap.unqualified);
        }
        holder.mItemEleListTv.setText(position + 1 + "  、" + bean.getStepName());
        holder.tvNfcType.setText(bean.getTermName());
        if (bean.isIsNFC() && !TextUtil.isEmpty(bean.getNFCNum())) {
            if (bean.isNfcScan()) {
                switch (position % 8 + 1) {
                    case 1:
                        holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label1);
                        break;
                    case 2:
                        holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label2);
                        break;
                    case 3:
                        holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label3);
                        break;
                    case 4:
                        holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label4);
                        break;
                    case 5:
                        holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label5);
                        break;
                    case 6:
                        holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label6);
                        break;
                    case 7:
                        holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label7);
                        break;
                    case 8:
                        holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label8);
                        break;
                }
            } else {
                holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label9);
            }
            holder.llNfcType.setVisibility(View.VISIBLE);
            holder.tvNfcType.setVisibility(View.VISIBLE);
        } else {
            holder.llNfcType.setVisibility(View.INVISIBLE);
            holder.tvNfcType.setVisibility(View.GONE);
        }

        holder.llQualified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivQualified.setImageResource(R.mipmap.unqualified);
                holder.ivUnqualified.setImageResource(R.mipmap.qualified);
                data.get(position).setSelectType(1);
                if (onCheckBoxClickListener != null) {
                    try {
                        onCheckBoxClickListener.onCheckBoxClick(v, position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        holder.llUnqualified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivQualified.setImageResource(R.mipmap.qualified);
                holder.ivUnqualified.setImageResource(R.mipmap.unqualified);
                data.get(position).setSelectType(2);
                if (onClickListener != null) {
                    try {
                        onClickListener.onClick(v, position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        holder.llNfcType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    try {
                        onItemClickListener.onItemClick(v, position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                    data.get(position).setDesc(String.valueOf(s));
                }
            }
        };
        holder.mItemEleListEt.addTextChangedListener(watcher);
        holder.mItemEleListEt.setTag(watcher);
        holder.mItemEleListEt.setText(data.get(position).getDesc());
        if (data.get(position).isIsTakePhoto()) {
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
        @BindView(R.id.llNfcType)
        LinearLayout llNfcType;
        @BindView(R.id.ivNfcType)
        ImageView ivNfcType;
        @BindView(R.id.tvNfcType)
        TextView tvNfcType;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
