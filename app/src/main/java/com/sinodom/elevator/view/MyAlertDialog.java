package com.sinodom.elevator.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinodom.elevator.R;

public class MyAlertDialog extends Dialog implements View.OnClickListener {

    protected Context context;
    protected View view;
    protected TextView tvTitle;
    protected TextView tvInfo;
    protected Button bConfirm;
    protected Button bCancel;
    protected View divider;

    public MyAlertDialog(Context context) {
        super(context);
        init(context);
    }

    public MyAlertDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    protected MyAlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.view_alert_dialog, null);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.label_notice);
        tvInfo = (TextView) view.findViewById(R.id.tvInfo);
        bCancel = (Button) view.findViewById(R.id.bCancel);
        bConfirm = (Button) view.findViewById(R.id.bConfirm);
        bConfirm.setText(R.string.action_confirm);
        divider = view.findViewById(R.id.divider);
        this.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
//        this.setCancelable(false);
        bCancel.setOnClickListener(this);
    }

    public void setOnConfirmListener(OnClickListener listener) {
        setOnConfirmListener(context.getString(R.string.action_confirm), listener);
    }

    public void setOnConfirmListener(String buttonText, final OnClickListener listener) {
        bConfirm.setText(buttonText);
        if (listener == null) {
            divider.setVisibility(View.GONE);
            bConfirm.setVisibility(View.GONE);
        } else {
            divider.setVisibility(View.VISIBLE);
            bConfirm.setVisibility(View.VISIBLE);
        }
        final MyAlertDialog that = this;
        bConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(that, v);
                }
            }
        });
    }

    public void setInfo(CharSequence info) {
        tvInfo.setText(info);
    }

    public void setInfo(int infoId) {
        tvInfo.setText(context.getString(infoId));
    }

    public void setInfo(String info) {
        tvInfo.setText(info);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        tvTitle.setText(context.getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
    }

    public interface OnClickListener {
        void onClick(MyAlertDialog dialog, View v);
    }

    public void setBCancelGone() {
        bCancel.setVisibility(View.GONE);
    }
}
