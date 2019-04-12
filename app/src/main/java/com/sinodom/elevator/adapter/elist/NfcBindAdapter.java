package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.nfc.NfcBean;
import com.sinodom.elevator.util.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * NFC绑定列表
 */
public class NfcBindAdapter extends BaseAdapter<NfcBean> {

    public NfcBindAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_nfc_bind, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final NfcBean bean = data.get(i);
        if (!TextUtil.isEmpty(bean.getNFCCode())) {
            switch (i % 8 + 1) {
                case 1:
                    holder.llNfcType.setBackgroundResource(R.mipmap.ic_nfc_label_bg1);
                    holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label1);
                    holder.tvNfcType.setText(bean.getTermName());
                    break;
                case 2:
                    holder.llNfcType.setBackgroundResource(R.mipmap.ic_nfc_label_bg2);
                    holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label2);
                    holder.tvNfcType.setText(bean.getTermName());
                    break;
                case 3:
                    holder.llNfcType.setBackgroundResource(R.mipmap.ic_nfc_label_bg3);
                    holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label3);
                    holder.tvNfcType.setText(bean.getTermName());
                    break;
                case 4:
                    holder.llNfcType.setBackgroundResource(R.mipmap.ic_nfc_label_bg4);
                    holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label4);
                    holder.tvNfcType.setText(bean.getTermName());
                    break;
                case 5:
                    holder.llNfcType.setBackgroundResource(R.mipmap.ic_nfc_label_bg5);
                    holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label5);
                    holder.tvNfcType.setText(bean.getTermName());
                    break;
                case 6:
                    holder.llNfcType.setBackgroundResource(R.mipmap.ic_nfc_label_bg6);
                    holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label6);
                    holder.tvNfcType.setText(bean.getTermName());
                    break;
                case 7:
                    holder.llNfcType.setBackgroundResource(R.mipmap.ic_nfc_label_bg7);
                    holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label7);
                    holder.tvNfcType.setText(bean.getTermName());
                    break;
                case 8:
                    holder.llNfcType.setBackgroundResource(R.mipmap.ic_nfc_label_bg8);
                    holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label8);
                    holder.tvNfcType.setText(bean.getTermName());
                    break;
            }

        } else {
            holder.llNfcType.setBackgroundResource(R.mipmap.ic_nfc_label_bg9);
            holder.ivNfcType.setBackgroundResource(R.mipmap.ic_nfc_label9);
            holder.tvNfcType.setText(bean.getTermName());
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
        @BindView(R.id.ivNfcType)
        ImageView ivNfcType;
        @BindView(R.id.tvNfcType)
        TextView tvNfcType;
        @BindView(R.id.llNfcType)
        LinearLayout llNfcType;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}