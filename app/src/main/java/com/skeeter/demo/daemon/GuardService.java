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
public class GuardService extends Service {
    private static final int DAEMON_SERVICE_ID = 123456789;
    private static GuardService sGuardService = null;

    @Override
    public void onCreate() {
        sGuardService = this;
        Log.e("wzw", "GuardService onCreate");
        super.onCreate();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            // API < 18时，直接传入new Notification()
            startForeground(DAEMON_SERVICE_ID, new Notification());
        } else {
            // API >= 18时，启动两个id相同的service，然后将后启动的service stop
            stopInnerService();
            startService(new Intent(this, GuardInnerService.class));
//            startForeground(DAEMON_SERVICE_ID, new Notification());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("wzw", "GuardService onStartCommand startId: " + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("wzw", "GuardService onDestroy");
        super.onDestroy();
        sGuardService = null;
    }

    public static boolean isAlive() {
        return sGuardService != null;
    }

    private void stopInnerService() {
        Intent innnerService = new Intent(this, GuardInnerService.class);
        try {
            stopService(innnerService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 用于API >= 18时灰色保活
     */
    public static class GuardInnerService extends Service {

        @Override
        public void onCreate() {
            super.onCreate();
            Log.e("wzw", "GuardInnerService onCreate");
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.e("wzw", "DaemonInnerService onStartCommand startId: " + startId);

            if (intent != null && GuardService.sGuardService != null) {
                GuardService.sGuardService.startForeground(DAEMON_SERVICE_ID, new Notification());
                startForeground(DAEMON_SERVICE_ID, new Notification());
                GuardService.sGuardService.stopForeground(true);
            }
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.e("wzw", "GuardInnerService onDestroy");
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
