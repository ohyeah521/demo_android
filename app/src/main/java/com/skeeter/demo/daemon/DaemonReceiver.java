package com.skeeter.demo.daemon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author michael created on 2017/1/10.
 */
public class DaemonReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("wzw", this + " action: " + intent.getAction());

//        startGuardService(context);
        startDaemonService(context);
    }

    private void startGuardService(Context context) {
        if (GuardService.isAlive()) {
            return;
        }

        Log.e("wzw", this + " startGuardService");
        Intent serviceIntent = new Intent(context, GuardService.class);
        context.startService(serviceIntent);
    }

    private void startDaemonService(Context context) {
        if (DaemonService.isAlive()) {
            return;
        }

        Log.e("wzw", this + " startDaemonService");
        Intent serviceIntent = new Intent(context, DaemonService.class);
        context.startService(serviceIntent);
    }
}
