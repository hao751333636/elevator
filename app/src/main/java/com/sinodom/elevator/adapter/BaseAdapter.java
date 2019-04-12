package com.sinodom.elevator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    protected LayoutInflater inflater;
    protected Context context;
    protected ListView listView;
    protected List<T> data;
    protected List<T> cacheData;

    protected OnClickListener onClickListener;
    protected OnCheckBoxClickListener onCheckBoxClickListener;
    protected OnItemClickListener onItemClickListener;
    protected OnItemLongClickListener onItemLongClickListener;
    protected OnCheckedChangeListener onCheckedChangeListener;

    public BaseAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = new ArrayList<T>();
    }

    public BaseAdapter(Context context, List<T> mlist) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = mlist;
    }

    public interface OnClickListener {
        void onClick(View v, int position);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(RadioGroup radioGroup, int position, int checkedId);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    public interface OnCheckBoxClickListener {
        void onCheckBoxClick(View v, int position);
    }

    public void setOnCheckBoxClickListener(OnCheckBoxClickListener onCheckBoxClickListener) {
        this.onCheckBoxClickListener = onCheckBoxClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View v, int position);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setData(List<T> data) {
        if (data == null) {
            data = new ArrayList<T>();
        }
        this.cacheData = data;
        this.data = data;
    }

    public List<T> getData() {
        if (this.data == null) {
            this.data = new ArrayList<T>();
        }
        return this.data;
    }

    /*
    Custom
     */

    public void updateRow(int position) {
        int start = listView.getFirstVisiblePosition();
        View view = listView.getChildAt(position - start + listView.getHeaderViewsCount());
        getView(position, view, listView);
    }

    /*
    Override
     */

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}