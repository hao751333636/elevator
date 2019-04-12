package com.sinodom.elevator.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinodom.elevator.R;
import com.sinodom.elevator.util.glide.GlideApp;

public class LoadView extends Dialog {

    protected View view;
    protected ImageView imageView;
    protected TextView textView;

    public LoadView(Context context) {
        super(context);
        init(context);
    }

    public LoadView(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    protected LoadView(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.view_load, null);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        GlideApp.with(context).load(R.mipmap.loading2).into(imageView);
        textView = (TextView) view.findViewById(R.id.tvText);
        this.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        this.setCancelable(true);
    }

    public void setInfo(String text) {
        textView.setText(text);
    }
}
