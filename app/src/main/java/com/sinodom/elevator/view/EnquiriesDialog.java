package com.sinodom.elevator.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.sinodom.elevator.R;

/**
 * Created by 安卓 on 2017/12/4.
 */

public class EnquiriesDialog extends Dialog {

    private Context context;
    private View customView;

    public EnquiriesDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
        LayoutInflater inflater= LayoutInflater.from(context);
        customView = inflater.inflate(R.layout.view_dialog_enquiries, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(customView);
    }
    @Override
    public View findViewById(int id) {
// TODO Auto-generated method stub
        return super.findViewById(id);
    }
    public View getCustomView() {
        return customView;
    }

}


