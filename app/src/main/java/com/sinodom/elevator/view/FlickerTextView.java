package com.sinodom.elevator.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class FlickerTextView extends TextView implements Runnable {

    private boolean mIsStop = false;
    private boolean mIsShow = false;
    private boolean flag = false;

    public FlickerTextView(Context context) {
        super(context);
    }

    public FlickerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlickerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        flag = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsShow) {
            if (flag && !mIsStop) {
                setBackgroundColor(Color.TRANSPARENT);
            } else {
                setBackgroundColor(Color.WHITE);
            }
        } else {
            setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void run() {
        while (!mIsStop) {
            synchronized (this) {
                try {
                    flag = !flag;
                    postInvalidate();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setIsStop(boolean isStop) {
        mIsStop = isStop;
        postInvalidate();
    }

    public void setIsShow(boolean isShow) {
        mIsShow = isShow;
        postInvalidate();
    }
}
