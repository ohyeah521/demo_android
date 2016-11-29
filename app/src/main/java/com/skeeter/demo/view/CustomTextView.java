package com.skeeter.demo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.skeeter.demo.Constant;

/**
 * @author michael created on 2016/11/17.
 */
public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(Constant.LOG_TAG,
                "textview dispatchTouchEvent with event.getAction : " + MotionEvent.actionToString(ev.getAction()));
        boolean result = super.dispatchTouchEvent(ev);
        Log.e(Constant.LOG_TAG, "textview dispatchTouchEvent result: " + result);
        return result;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e(Constant.LOG_TAG,
                "textview onTouchEvent with event.getAction : " + MotionEvent.actionToString(ev.getAction()));
        boolean result = super.onTouchEvent(ev);
        Log.e(Constant.LOG_TAG, "textview onTouchEvent result: " + result);
        return result;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(Constant.LOG_TAG, "textview onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e(Constant.LOG_TAG, "textview onLayout");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(Constant.LOG_TAG, "textview onDraw");
        super.onDraw(canvas);
    }


}
