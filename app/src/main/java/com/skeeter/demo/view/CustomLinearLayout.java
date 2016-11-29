package com.skeeter.demo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.skeeter.demo.Constant;

/**
 * @author michael created on 2016/11/17.
 */
public class CustomLinearLayout extends LinearLayout {
    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(Constant.LOG_TAG,
                "layout dispatchTouchEvent with event.getAction : " + MotionEvent.actionToString(ev.getAction()));
        boolean result = super.dispatchTouchEvent(ev);
        Log.e(Constant.LOG_TAG, "layout dispatchTouchEvent result: " + result);
        return result;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e(Constant.LOG_TAG,
                "layout onInterceptTouchEvent with event.getAction : " + MotionEvent.actionToString(ev.getAction()));
        boolean result = super.onInterceptTouchEvent(ev);
        Log.e(Constant.LOG_TAG, "layout onInterceptTouchEvent result: " + result);
        return result;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(Constant.LOG_TAG,
                "layout onTouchEvent with event.getAction : " + MotionEvent.actionToString(event.getAction()));
        boolean result = super.onTouchEvent(event);
        Log.e(Constant.LOG_TAG, "layout onTouchEvent result: " + result);
        return result;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        Log.e(Constant.LOG_TAG, "layout requestDisallowInterceptTouchEvent : " + disallowIntercept);
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(Constant.LOG_TAG, "layout onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e(Constant.LOG_TAG, "layout onLayout");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(Constant.LOG_TAG, "layout onDraw");
        super.onDraw(canvas);
    }
}
