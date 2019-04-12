package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.business.RecordDetailsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 安卓 on 2017/11/2.
 * 电梯维保 电梯检验调查
 */

public class ElevatorSurveyAdapter extends BaseAdapter<RecordDetailsBean.CheckDetailsBean.StepBean> {

    private LayoutInflater inflater;
    private MyInterface myInterface;

    private int[] elect;
    private String[] et;

    public ElevatorSurveyAdapter(Context context, List<RecordDetailsBean.CheckDetailsBean.StepBean> mList) {
        super(context, mList);
        this.elect = new int[data.size()];
        this.et = new String[data.size()];
        inflater = LayoutInflater.from(context);
    }

    public void setElevatorSurveyAdapter(MyInterface myInterface) {
        this.myInterface = myInterface;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_elevator_survey, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mItemEleListTv.setText(position + 1 + "  、" + data.get(position).getStepName());

        if (elect[position] == 0) {
            holder.ivQualified.setImageResource(R.mipmap.qualified);
            holder.ivUnqualified.setImageResource(R.mipmap.qualified);
        } else if (elect[position] == 1) {
            holder.ivQualified.setImageResource(R.mipmap.unqualified);
            holder.ivUnqualified.setImageResource(R.mipmap.qualified);
        } else if (elect[position] == 2) {
            holder.ivQualified.setImageResource(R.mipmap.qualified);
            holder.ivUnqualified.setImageResource(R.mipmap.unqualified);
        } else {
            holder.ivQualified.setImageResource(R.mipmap.qualified);
            holder.ivUnqualified.setImageResource(R.mipmap.qualified);
        }

        holder.llQualified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入照相
                elect[position] = 1;
                myInterface.getElect(elect);
                holder.ivQualified.setImageResource(R.mipmap.unqualified);
                holder.ivUnqualified.setImageResource(R.mipmap.qualified);
                Judgment(position, true);
            }
        });
        holder.llUnqualified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入照相
                elect[position] = 2;
                myInterface.getElect(elect);
                holder.ivQualified.setImageResource(R.mipmap.qualified);
                holder.ivUnqualified.setImageResource(R.mipmap.unqualified);
                Judgment(position, false);
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
                    et[position] = String.valueOf(s);
                }
            }
        };
        holder.mItemEleListEt.addTextChangedListener(watcher);
        holder.mItemEleListEt.setTag(watcher);
        holder.mItemEleListEt.setText(et[position]);
        if (data.get(position).isIsTakePhoto()) {
            holder.ivPictures.setVisibility(View.VISIBLE);
        } else {
            holder.ivPictures.setVisibility(View.GONE);
        }

        return convertView;
    }

    public interface MyInterface {
        void foo(int position, boolean elects);

        void getElect(int[] elect);
    }

    public void setElect(int[] elect) {
        this.elect = elect;
        notifyDataSetChanged();
    }

    private void Judgment(int position, Boolean elects) {
        //进入照相
        myInterface.foo(position, elects);

    }

    public String[] getEt() {
        return et;
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
