// ICompute.aidl
package com.skeeter.demo.ipc.aidl;

// Declare any non-default types here with import statements

interface IAdd {
    /**
    * @return result of x+y
    * */
    int add(int x, int y);
}
