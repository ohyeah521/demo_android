package com.skeeter.demo.ipc.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.skeeter.demo.R;
import com.skeeter.demo.ipc.aidl.IAdd;
import com.skeeter.demo.ipc.server.AddService;

/**
 * @author michael created on 2016/11/27.
 */
public class IpcTestAct extends FragmentActivity {

    private IAdd mAdd;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mAdd = IAdd.Stub.asInterface(service);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mAdd.asBinder().linkToDeath(mDeathRecipient, 0);

                        int result = mAdd.add(3, 4);
                        Log.e("ipc", "result: " + result);

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e("ipc", "DeathRecipient binderDied in thread: " + Thread.currentThread());
            if (mAdd == null) {
                return;
            }
            mAdd.asBinder().unlinkToDeath(this, 0);

            bind();
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_empty);


        bind();
    }


    private void bind() {
        Intent intent;
        intent = new Intent(this, AddService.class);

        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

}
