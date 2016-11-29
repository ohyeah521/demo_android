package com.skeeter.demo.thread;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.skeeter.demo.Constant;

/**
 * @author michael created on 2016/11/22.
 */
public class CustomIntentService extends IntentService {

    public CustomIntentService() {
        super(CustomIntentService.class.getSimpleName());
    }

    public CustomIntentService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(Constant.LOG_TAG, "CustomIntentService onCreate thread: " + Thread.currentThread());
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e(Constant.LOG_TAG, "CustomIntentService onStart thread: " + Thread.currentThread());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(Constant.LOG_TAG, "CustomIntentService onStartCommand thread: " + Thread.currentThread());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e(Constant.LOG_TAG, "CustomIntentService onHandleIntent thread: " + Thread.currentThread());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(Constant.LOG_TAG, "CustomIntentService onDestroy thread: " + Thread.currentThread());
    }
}
