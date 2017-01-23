package com.skeeter.demo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * @author michael created on 2017/1/5.
 */
public class Count {
    public static final String MY_ACTION = "TestBroadcast.MY_ACTION";

    public static int sInt = 0;

    public Context mContext;
    public BroadcastReceiver mReceiver;

    public Count(Context context) {
        mContext = context.getApplicationContext();
    }

    public void register() {
        sInt++;
        final int a = sInt;
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.e("wzw", "sInt: " + sInt);
                Log.e("wzw", "a: " + a);
                Log.e("wzw", "-------");
            }
        };
        mContext.registerReceiver(mReceiver, new IntentFilter(MY_ACTION));
    }
}
