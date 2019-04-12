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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.adapter.BaseAdapter;
import com.sinodom.elevator.bean.inspect.InspectItemBean;
import com.sinodom.elevator.util.TextUtil;
import com.sinodom.elevator.view.TimeSelector;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InspectItemAdapter extends BaseAdapter<InspectItemBean.InspectStepBean> {

    private MyInterface myInterface;
    private TimeSelector timeSelector;

    public InspectItemAdapter(Context context) {
        super(context);
    }

    public void setElevatorListNewAdapter(MyInterface myInterface) {
        this.myInterface = myInterface;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_inspect_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final InspectItemBean.InspectStepBean bean = data.get(position);
        holder.tvTitle.setText(position + 1 + "  、" + bean.getStepName());
//        holder.itemEleListEt.setText(bean.getRemark());

        if (bean.isIsPhoto()) {
            holder.rlHandle.setVisibility(View.VISIBLE);
            holder.itemEleListEt.setVisibility(View.VISIBLE);
            //拍照
            holder.ivCamera.setImageResource(R.mipmap.ic_maintenance_photograph);
            if (TextUtil.isEmpty(bean.getPhotoUrl())) {
                holder.ivNetwork.setImageResource(R.mipmap.ic_maintenance_photo1);
            } else {
                holder.ivNetwork.setImageResource(R.mipmap.ic_maintenance_photo1sel);
            }
            if (TextUtil.isEmpty(bean.getLocalPhotoUrl())) {
                holder.ivLocal.setImageResource(R.mipmap.ic_maintenance_photo2);
            } else {
                holder.ivLocal.setImageResource(R.mipmap.ic_maintenance_photo2sel);
            }
            if (TextUtil.isEmpty(bean.getOffLinePhotoUrl())) {
                holder.ivOffLine.setImageResource(R.mipmap.ic_inspect_off);
            } else {
                holder.ivOffLine.setImageResource(R.mipmap.ic_inspect_off_sel);
            }
        } else if(bean.isIsVideo()){
            holder.rlHandle.setVisibility(View.VISIBLE);
            holder.itemEleListEt.setVisibility(View.VISIBLE);
            //录像
            holder.ivCamera.setImageResource(R.mipmap.ic_maintenance_videotape);
            if (TextUtil.isEmpty(bean.getVideoPath())) {
                holder.ivNetwork.setImageResource(R.mipmap.ic_maintenance_photo1);
            } else {
                holder.ivNetwork.setImageResource(R.mipmap.ic_maintenance_photo1sel);
            }
            if (TextUtil.isEmpty(bean.getLocalVideoPath())) {
                holder.ivLocal.setImageResource(R.mipmap.ic_maintenance_photo2);
            } else {
                holder.ivLocal.setImageResource(R.mipmap.ic_maintenance_photo2sel);
            }
            if (TextUtil.isEmpty(bean.getOffLineVideoPath())) {
                holder.ivOffLine.setImageResource(R.mipmap.ic_inspect_off);
            } else {
                holder.ivOffLine.setImageResource(R.mipmap.ic_inspect_off_sel);
            }
        }else{
            holder.rlHandle.setVisibility(View.GONE);
            holder.itemEleListEt.setVisibility(View.GONE);
        }

        holder.llTemplate.removeAllViews();
        if (bean.getInspectTemplateAttributeEntityList() != null) {
            for (int i = 0; i < bean.getInspectTemplateAttributeEntityList().size(); i++) {
                if (bean.getInspectTemplateAttributeEntityList().get(i).getAttributeType().equals("文本")) {
                    EditText et = new EditText(context);
                    et.setTextSize(14);
                    if (TextUtil.isEmpty(bean.getInspectTemplateAttributeEntityList().get(i).getAttributeUnits())) {
                        et.setHint(bean.getInspectTemplateAttributeEntityList().get(i).getAttributeName());
                    } else {
                        et.setHint(bean.getInspectTemplateAttributeEntityList().get(i).getAttributeName() + "（" + bean.getInspectTemplateAttributeEntityList().get(i).getAttributeUnits() + "）");
                    }
                    holder.llTemplate.addView(et);

                    removeTextWatcher(et);
                    final int finalI = i;
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
                                bean.getInspectTemplateAttributeEntityList().get(finalI).setInputValue(String.valueOf(s));
                            }
                        }
                    };
                    et.addTextChangedListener(watcher);
                    et.setTag(watcher);
                    et.setText(bean != null ? bean.getInspectTemplateAttributeEntityList().get(i).getInputValue() : "");
                } else if (bean.getInspectTemplateAttributeEntityList().get(i).getAttributeType().equals("日期")) {
                    final TextView tv = new TextView(context);
                    tv.setTextSize(14);
                    tv.setPadding(10, 10, 10, 10);
                    tv.setHint(bean.getInspectTemplateAttributeEntityList().get(i).getAttributeName());
                    final int finalI1 = i;
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            timeSelector = new TimeSelector(context, new TimeSelector.ResultHandler() {
                                @Override
                                public void handle(String time) {
                                    tv.setText(time);
                                    bean.getInspectTemplateAttributeEntityList().get(finalI1).setInputValue(time);
                                }
                            }, "1900-01-01 00:00", "2049-12-31 23:59");
                            timeSelector.show();
                        }
                    });
                    holder.llTemplate.addView(tv);

                    removeTextWatcher(tv);
                    final int finalI = i;
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
                                bean.getInspectTemplateAttributeEntityList().get(finalI).setInputValue(String.valueOf(s));
                            }
                        }
                    };
                    tv.addTextChangedListener(watcher);
                    tv.setTag(watcher);
                    tv.setText(bean != null ? bean.getInspectTemplateAttributeEntityList().get(i).getInputValue() : "");
                }
            }
        }

        //拍摄
        holder.ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.clickCamera(position);
            }
        });
        //确认
        holder.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.clickSubmit(position);
            }
        });
        //服务器
        holder.ivNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.clickNetwork(position);
            }
        });
        //本地
        holder.ivLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.clickLocal(position);
            }
        });
        //离线
        holder.ivOffLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myInterface.clickOffLine(position);
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
                    bean.setRemark(String.valueOf(s));
                }
            }
        };
        holder.itemEleListEt.addTextChangedListener(watcher);
        holder.itemEleListEt.setTag(watcher);
        holder.itemEleListEt.setText(bean != null ? bean.getRemark() : "");

        return convertView;
    }

    //自定义接口
    public interface MyInterface {

        void clickNetwork(int position);//查看网络图片或视频

        void clickLocal(int position); //查看本地照片或视频

        void clickOffLine(int position); //查看缓存照片或视频

        void clickCamera(int position);  //拍摄

        void clickSubmit(int position); //确认
    }

    private void removeTextWatcher(TextView editText) {
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
        @BindView(R.id.ivOffLine)
        ImageView ivOffLine;    //离线照片
        @BindView(R.id.ivCamera)
        ImageView ivCamera;   //拍摄
        @BindView(R.id.item_ele_list_et)
        EditText itemEleListEt;   //备注
        @BindView(R.id.tvSubmit)
        TextView tvSubmit;   //确认
        @BindView(R.id.llTemplate)
        LinearLayout llTemplate;
        @BindView(R.id.rlHandle)
        RelativeLayout rlHandle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
