package com.skeeter.demo.broadcast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * @author michael created on 2017/1/5.
 */
public class TestBroadcast extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Count(this).register();

    }
}
