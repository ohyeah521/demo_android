package com.skeeter.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * @author michael created on 2017/1/9.
 */
public class Activity6 extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("wzw", this.toString() + " , " + getTaskId());

        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }
}
