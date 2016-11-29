package com.skeeter.demo.ipc.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author michael created on 2016/11/27.
 */
public class AddService extends Service {

    private Binder mBinder = new AddImpl();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("ipc", "AddService onCreate with thread: " + Thread.currentThread());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("ipc", "AddService onBind with intent: " + intent);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                Process.killProcess(Process.myPid());
            }
        }).start();

        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("ipc", "AddService onUnbind with intent: " + intent);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("ipc", "AddService onDestroy");
    }
}
