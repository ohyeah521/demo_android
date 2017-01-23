package com.skeeter.demo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.skeeter.demo.broadcast.Count;
import com.skeeter.demo.broadcast.TestBroadcast;
import com.skeeter.demo.daemon.DaemonReceiver;
import com.skeeter.demo.daemon.GuardService;
import com.skeeter.demo.multidown.MultiDownUtil;
import com.skeeter.demo.reflect.JavaCalls;
import com.skeeter.demo.daemon.DaemonService;
import com.skeeter.demo.view.CustomLinearLayout;
import com.skeeter.demo.view.CustomTextView;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public class MainActivity extends AppCompatActivity {
    private CustomLinearLayout mLayoutContainer;
    private CustomTextView mTVTestClick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayoutContainer = (CustomLinearLayout) findViewById(R.id.act_main_container_layout);
        mTVTestClick = (CustomTextView) findViewById(R.id.act_main_view_testclick);

//        testViewClick();

//        Intent intent = new Intent(this, IpcTestAct.class);
//        startActivity(intent);


//        Intent intent = new Intent(this, CustomBroadcastReceiver.class);
////        sendBroadcast(intent);
//        intent.setAction(CustomBroadcastReceiver.class.getSimpleName());
//
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

//        testRL();

//        testVM();

        findViewById(R.id.act_main_view_goto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestBroadcast.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.act_main_view_broadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Count.MY_ACTION);
                Intent intent = new Intent("DaemonService");
                sendBroadcast(intent);
            }
        });

        testDaemonService();

    }

    protected void testDaemonService() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(new DaemonReceiver(), filter);

        //        Intent intent = new Intent(this, GuardService.class);
        Intent intent = new Intent(this, DaemonService.class);
        startService(intent);
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

//        new AlertDialog.Builder(this).setIcon().create();

    }

    private void testRL() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
            }
        };

        findViewById(R.id.main_rl_view1).setOnClickListener(listener);
        findViewById(R.id.main_rl_view2).setOnClickListener(listener);
        findViewById(R.id.main_rl_view3).setOnClickListener(listener);

        findViewById(R.id.secondLine).setOnClickListener(listener);

//        TextView textView = (TextView) findViewById(R.id.main_rl_view1);
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();

//        params.alignWithParent

    }

    private final static long MIN_HEAP_SIZE = 6 * 1024 * 1024;

    private final static float TARGET_HEAP_UTILIZATION = 0.75f;

    private void testVM() {
        try {
            Class<?> VMRuntime = Class.forName("dalvik.system.VMRuntime");
            Object vm = JavaCalls.callStaticMethodOrThrow(VMRuntime, "getRuntime");
            JavaCalls.callMethod(vm, "setTargetHeapUtilization", TARGET_HEAP_UTILIZATION);

            Log.e("wzw", "vm heapsize: after set " + JavaCalls.callMethod(vm, "getTargetHeapUtilization"));


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
