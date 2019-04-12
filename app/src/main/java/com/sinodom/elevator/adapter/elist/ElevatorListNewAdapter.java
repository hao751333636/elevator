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
import com.sinodom.elevator.bean.elevator.ExamineDataBean;
import com.sinodom.elevator.bean.steplist.StepDataBean;
import com.sinodom.elevator.util.TextUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by 安卓 on 2017/11/2.
 */

public class ElevatorListNewAdapter extends BaseAdapter<ExamineDataBean> {

    private MyInterface myInterface;
    private StepDataBean.InspectDetailsBean[] mStatusBean;

    public ElevatorListNewAdapter(Context context, List<ExamineDataBean> mList, StepDataBean.InspectDetailsBean[] statusBean) {
        super(context, mList);
        this.mStatusBean = statusBean;
    }

    public void setElevatorListNewAdapter(MyInterface myInterface) {
        this.myInterface = myInterface;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_elevator_list_new, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(data.get(position).getID() + "  、" + data.get(position).getStepName());

        if (data.get(position).isIsPhoto() == true) {
            //拍照
            holder.ivCamera.setImageResource(R.mipmap.ic_maintenance_photograph);
            if (mStatusBean[position] == null || TextUtil.isEmpty(mStatusBean[position].getPhotoUrl())) {
                holder.ivNetwork.setImageResource(R.mipmap.ic_maintenance_photo1);
            } else {
                holder.ivNetwork.setImageResource(R.mipmap.ic_maintenance_photo1sel);
            }
            if (mStatusBean[position] == null || TextUtil.isEmpty(mStatusBean[position].getPhotoLocalUrl())) {
                holder.ivLocal.setImageResource(R.mipmap.ic_maintenance_photo2);
            } else {
                holder.ivLocal.setImageResource(R.mipmap.ic_maintenance_photo2sel);
            }
        } else {
            //录像
            holder.ivCamera.setImageResource(R.mipmap.ic_maintenance_videotape);
            if (mStatusBean[position] == null || TextUtil.isEmpty(mStatusBean[position].getVideoPath())) {
                holder.ivNetwork.setImageResource(R.mipmap.ic_maintenance_photo1);
            } else {
                holder.ivNetwork.setImageResource(R.mipmap.ic_maintenance_photo1sel);
            }
            if (mStatusBean[position] == null || TextUtil.isEmpty(mStatusBean[position].getVideoLocalPath())) {
                holder.ivLocal.setImageResource(R.mipmap.ic_maintenance_photo2);
            } else {
                holder.ivLocal.setImageResource(R.mipmap.ic_maintenance_photo2sel);
            }
        }

        if (mStatusBean[position] == null || mStatusBean[position].getIsLock() == 0) {
            holder.tvLock.setText("未锁定");
            holder.ivLock.setImageResource(R.mipmap.ic_maintenance_unlock);
        } else if (mStatusBean[position] == null || mStatusBean[position].getIsLock() == 1) {
            holder.tvLock.setText("已锁定");
            holder.ivLock.setImageResource(R.mipmap.ic_maintenance_locking);
        }

        //拍摄
        holder.ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.setPosition(position);
                myInterface.clickCamera();
            }
        });
        //确认
        holder.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.setPosition(position);
                myInterface.clickSubmit("" + holder.itemEleListEt.getText().toString().trim());
            }
        });
        //锁
        holder.llLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.setPosition(position);
                myInterface.clickLock();
            }
        });
        //服务器
        holder.ivNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.setPosition(position);
                myInterface.clickNetwork();
            }
        });
        //本地
        holder.ivLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.setPosition(position);
                myInterface.clickLocal();
            }
        });

        removeTextWatcher(holder.itemEleListEt);
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
                    mStatusBean[position].setRemark(String.valueOf(s));
                }
            }
        };
        holder.itemEleListEt.addTextChangedListener(watcher);
        holder.itemEleListEt.setTag(watcher);
        holder.itemEleListEt.setText(mStatusBean[position] != null ? mStatusBean[position].getRemark() : "");

        return convertView;
    }

    //自定义接口
    public interface MyInterface {
        void setPosition(int position);//传递当前是第几条

        void clickNetwork();//查看网络图片或视频

        void clickLocal(); //查看本地照片或视频

        void clickCamera();  //拍摄

        void clickLock();  //锁

        void clickSubmit(String note); //确认
    }

    public void setRefresh(StepDataBean.InspectDetailsBean[] statusBean) {
        this.mStatusBean = statusBean;
        notifyDataSetChanged();
    }

    private void removeTextWatcher(EditText editText) {
        if (editText.getTag() instanceof TextWatcher) {
            editText.removeTextChangedListener((TextWatcher) editText.getTag());
        }
    }

    static class ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;  //标题
        @BindView(R.id.ivNetwork)
        ImageView ivNetwork;  //服务器照片
        @BindView(R.id.ivLocal)
        ImageView ivLocal;    //本地照片
        @BindView(R.id.ivCamera)
        ImageView ivCamera;   //拍摄
        @BindView(R.id.item_ele_list_et)
        EditText itemEleListEt;   //备注
        @BindView(R.id.ivLock)
        ImageView ivLock;    //锁 图片
        @BindView(R.id.tvLock)
        TextView tvLock;   // 锁  文字
        @BindView(R.id.llLock)
        LinearLayout llLock;  //锁  布局
        @BindView(R.id.tvSubmit)
        TextView tvSubmit;   //确认

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
