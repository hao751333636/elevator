package com.sinodom.elevator.adapter.elist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.bean.elevator.ExamineDataBean;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;

    private List<ExamineDataBean> mList;
    private List<ExamineDataBean> invisibleChildren;

    public ExpandableListAdapter(List<ExamineDataBean> data) {
        this.mList = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        switch (type) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_header, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
                TextView itemTextView = new TextView(context);
                itemTextView.setPadding(subItemPaddingLeft, subItemPaddingTopAndBottom, 0, subItemPaddingTopAndBottom);
                itemTextView.setTextColor(0x88000000);
                itemTextView.setLayoutParams(
                        new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                return new RecyclerView.ViewHolder(itemTextView) {
                };
        }
        return null;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ExamineDataBean item = mList.get(position);
        switch (item.getTypeID()) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;
                itemController.refferalItem = item;
                itemController.header_title.setText(item.getTypeName());
                if (item.getTypeName() == null) {
                    itemController.btn_expand_toggle.setImageResource(R.mipmap.ic_launcher);
                } else {
                    itemController.btn_expand_toggle.setImageResource(R.mipmap.ic_launcher);
                }
                itemController.btn_expand_toggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.getTypeName() == null) {
                            mList = new ArrayList<ExamineDataBean>();
                            int count = 0;
                            int pos = mList.indexOf(itemController.refferalItem);
                            while (mList.size() > pos + 1 && mList.get(pos + 1).getID() == CHILD) {
                                invisibleChildren.add(mList.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.mipmap.ic_launcher);
                        } else {
                            int pos = mList.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (ExamineDataBean i : invisibleChildren) {
                                mList.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.mipmap.ic_launcher);
                            invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD:
                TextView itemTextView = (TextView) holder.itemView;
                itemTextView.setText(mList.get(position).getStepName());
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getTypeID();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public ImageView btn_expand_toggle;
        public ExamineDataBean refferalItem;
        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.header_title);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.btn_expand_toggle);
        }
    }
    public static class Item {
        public int type;
        public String text;
        public List<ExamineDataBean> invisibleChildren;

        public Item() {
        }

        public Item(int type, String text) {
            this.type = type;
            this.text = text;
        }
    }

}
