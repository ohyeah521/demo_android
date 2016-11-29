package com.skeeter.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.skeeter.demo.broadcast.CustomBroadcastReceiver;
import com.skeeter.demo.ipc.client.IpcTestAct;
import com.skeeter.demo.multidown.MultiDownUtil;
import com.skeeter.demo.thread.AsyncTaskActivity;
import com.skeeter.demo.view.CustomLinearLayout;
import com.skeeter.demo.view.CustomTextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private CustomLinearLayout mLayoutContainer;
    private CustomTextView mTVTestClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayoutContainer = (CustomLinearLayout) findViewById(R.id.act_main_container_layout);
        mTVTestClick = (CustomTextView) findViewById(R.id.act_main_view_testclick);

        testViewClick();

        Intent intent = new Intent(this, IpcTestAct.class);
        startActivity(intent);


//        Intent intent = new Intent(this, CustomBroadcastReceiver.class);
////        sendBroadcast(intent);
//        intent.setAction(CustomBroadcastReceiver.class.getSimpleName());
//
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }

    private void testViewClick() {
        mLayoutContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e(Constant.LOG_TAG, "layout onLongClick");
                v.requestLayout();
                return true;
            }
        });

        mTVTestClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(Constant.LOG_TAG, "textview onClick");
                Toast.makeText(MainActivity.this, "textview onClick", Toast.LENGTH_SHORT).show();
                v.invalidate();
            }
        });
//        mTVTestClick.setLongClickable(false);
        mTVTestClick.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }


    private void testDownload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(Environment.getExternalStorageDirectory(), "demo");
                file.mkdirs();
                file = new File(file, "chunyu.apk");
                String downloadUrl = "http://media2.chunyuyisheng.com/media/apk/SpringRainDoctor_8.3.2_chunyu.apk";
//                file = new File(file, "temp.json");
//                String downloadUrl = "http://biztest.chunyu.mobi/community/v2/channel/detail/?page=1";
                MultiDownUtil.newInstance(file.getAbsolutePath(), downloadUrl)
//                        .setThreadDownLength(10)
                        .setMaxThreadCount(5)
                        .download();

            }
        }).start();

    }

}
