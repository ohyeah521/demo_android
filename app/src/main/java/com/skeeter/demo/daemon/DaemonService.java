package com.skeeter.demo.daemon;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author michael created on 2017/1/10.
 */
public class DaemonService extends Service {
    private static final int DAEMON_SERVICE_ID = 123456789;
    private static boolean mAlive = false;

    @Override
    public void onCreate() {
        Log.e("wzw", "DaemonService onCreate");
        super.onCreate();
        mAlive = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("wzw", "DaemonService onStartCommand startId: " + startId);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            // API < 18时，直接传入new Notification()
            startForeground(DAEMON_SERVICE_ID, new Notification());
        } else {
            // API >= 18时，启动两个id相同的service，然后将后startForeground的service stopForeground/stop
            startService(new Intent(this, DaemonInnerService.class));
            startForeground(DAEMON_SERVICE_ID, new Notification());
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("wzw", "DaemonService onDestroy");
        super.onDestroy();
        mAlive = false;
    }

    public static boolean isAlive() {
        return mAlive;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 用于API >= 18时灰色保活Service
     */
    public static class DaemonInnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.e("wzw", "DaemonInnerService onStartCommand startId: " + startId);
            startForeground(DAEMON_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
