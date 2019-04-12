package com.sinodom.elevator.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.orhanobut.logger.Logger;

public class JNViewPager extends ViewPager {

    public JNViewPager(Context context) {
        super(context);
    }

    public JNViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean b = false;
        try {
            b = super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            Logger.e(e.getMessage());
        }
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean b = false;
        try {
            b = super.onTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            Logger.e(e.getMessage());
        }
        return b;
    }
}
