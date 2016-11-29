package com.skeeter.demo.ipc.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @author michael created on 2016/11/27.
 */
public class BookManagerService extends Service {
    IBinder mBinder = new BookManager();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
