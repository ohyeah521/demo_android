package com.skeeter.demo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.skeeter.demo.Constant;

/**
 * @author michael created on 2016/11/25.
 */
public class CustomBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(Constant.LOG_TAG, "CustomBroadcastReceiver received, intent: " + intent);

    }
}
