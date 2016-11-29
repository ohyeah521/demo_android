// IBinderPool.aidl
package com.skeeter.demo.ipc.aidl;

// Declare any non-default types here with import statements

interface IBinderPool {
    /**
    * @param binderCode : the unique code for specific Binder
    * @return the Binder who's code is binderCode
    * */
    IBinder queryBinder(int binderCode);
}
