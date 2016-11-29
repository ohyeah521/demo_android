package com.skeeter.demo.ipc.server;

import android.os.RemoteException;
import android.util.Log;

import com.skeeter.demo.ipc.aidl.IAdd;


/**
 * @author michael created on 2016/11/27.
 */
class AddImpl extends IAdd.Stub {
    @Override
    public int add(int x, int y) throws RemoteException {
        Log.e("ipc", "AddImpl add invoked with thread: " + Thread.currentThread());
        return x + y;
    }
}
