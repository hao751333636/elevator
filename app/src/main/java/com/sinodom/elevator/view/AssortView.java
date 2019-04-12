package com.sinodom.elevator.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.sinodom.elevator.R;

public class AssortView extends AppCompatButton {

    private Context context;

    public interface OnTouchAssortListener {
        void onTouchAssortListener(String s);

        void onTouchAssortUP();
    }

    public AssortView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public AssortView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AssortView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setAssort(String[] assort) {
        this.assort = assort;
    }

    // 分类
//    private String[] assort = {"#", "A", "B", "C", "D", "E", "F", "G",
//            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
//            "U", "V", "W", "X", "Y", "Z"};
    private String[] assort = {};
    private Paint paint = new Paint();
    // 选择的索引
    private int selectIndex = -1;
    // 字母监听器
    private OnTouchAssortListener onTouch;

    private void init() {

    }


    public void setOnTouchAssortListener(OnTouchAssortListener onTouch) {
        this.onTouch = onTouch;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (assort.length == 0) {
            return;
        }

        int height = getHeight();
        int width = getWidth();
        int interval = height / assort.length;

        for (int i = 0, length = assort.length; i < length; i++) {
            // 抗锯齿
            paint.setAntiAlias(true);
            paint.setTypeface(Typeface.DEFAULT);
            paint.setColor(ContextCompat.getColor(context, R.color.c_main));
            paint.setTextSize(getResources().getDimension(R.dimen.dimen_14sp));
            if (i == selectIndex) {
                // 被选择的字母改变颜色和粗体
                paint.setColor(ContextCompat.getColor(context, R.color.c_main_dark));
                paint.setFakeBoldText(true);
                paint.setTextSize(getResources().getDimension(R.dimen.dimen_22sp));
            }

            float mt = paint.measureText(assort[i]);
            // 计算字母的X坐标
            float xPos = width / 2 - mt / 2;
            // 计算字母的Y坐标
            float yPos = interval * i + interval / 2 + mt / 2;
            canvas.drawText(assort[i], xPos, yPos, paint);
            paint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float y = event.getY();
        int index = (int) (y / getHeight() * assort.length);
        if (index >= 0 && index < assort.length) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    // 如果滑动改变
                    if (selectIndex != index) {
                        selectIndex = index;
                        if (onTouch != null) {
                            onTouch.onTouchAssortListener(assort[selectIndex]);
                        }
                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                    selectIndex = index;
                    if (onTouch != null) {
                        onTouch.onTouchAssortListener(assort[selectIndex]);
                    }
                    setBackgroundColor(ContextCompat.getColor(context, R.color.c_bg_light2));
                    break;
                case MotionEvent.ACTION_UP:
                    if (onTouch != null) {
                        onTouch.onTouchAssortUP();
                    }
                    selectIndex = -1;
                    setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
                    break;
            }
        } else {
            selectIndex = -1;
            if (onTouch != null) {
                onTouch.onTouchAssortUP();
            }
        }
        invalidate();

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

}
