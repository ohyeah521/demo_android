package com.skeeter.demo.thread;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.PrintStreamPrinter;
import android.util.Printer;

import com.skeeter.demo.Constant;

import java.lang.reflect.Field;

/**
 * @author michael created on 2016/11/21.
 */
public class AsyncTaskActivity extends FragmentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        testAsyncTask();
//        testIntentService();
//        testLooper();
    }


    private void testAsyncTask() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                final CustomAsyncTask asyncTask = new CustomAsyncTask();
                Handler mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        Log.e(Constant.LOG_TAG, "msg.what: " + msg.what);
                        if (msg.what > 50) {
                            asyncTask.cancel(true);
                        }
                    }
                };
                asyncTask.setHandler(mHandler);

                asyncTask.execute();
            }
        }).start();

    }

    private void testIntentService() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AsyncTaskActivity.this, CustomIntentService.class);

                // 顺序执行，只执行一次OnCreate 执行完后执行onDestroy
                startService(intent);
                startService(intent);
                startService(intent);

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // intentservcice onDestory后再次startService会重新onCreate
                startService(intent);

            }
        }).start();
    }


    private void testLooper() {
        Thread thread = new Thread() {
            CustomHandler mHandler;

            @Override
            public void run() {
                super.run();

                Looper.prepare();
                Printer printer = new PrintStreamPrinter(System.out);
                Looper.myLooper().setMessageLogging(printer);

                mHandler = new CustomHandler(Looper.myLooper());

                long sendTime = SystemClock.uptimeMillis() + 100;

                mHandler.sendEmptyMessage(0);
                Log.e(Constant.LOG_TAG, "messageQueue: " + printMessageQueue(Looper.myLooper()));
                mHandler.sendEmptyMessageAtTime(1, sendTime);
                Log.e(Constant.LOG_TAG, "messageQueue: " + printMessageQueue(Looper.myLooper()));
                mHandler.sendEmptyMessageAtTime(2, sendTime);
                Log.e(Constant.LOG_TAG, "messageQueue: " + printMessageQueue(Looper.myLooper()));
                mHandler.sendEmptyMessageAtTime(3, sendTime);
                Log.e(Constant.LOG_TAG, "messageQueue: " + printMessageQueue(Looper.myLooper()));

                Looper.loop();
            }
        };
        thread.start();
    }

    private String printMessageQueue(Looper looper) {
        StringBuilder builder = new StringBuilder();
        try {
            Field msgQueueField = Looper.class.getDeclaredField("mQueue");
            msgQueueField.setAccessible(true);
            MessageQueue msgQueue = (MessageQueue) msgQueueField.get(looper);

            Field messagesField = MessageQueue.class.getDeclaredField("mMessages");
            messagesField.setAccessible(true);
            Field nextField = Message.class.getDeclaredField("next");
            nextField.setAccessible(true);

            Message p = (Message) messagesField.get(msgQueue);
            while (p != null) {
                builder.append(p).append(" ,\n");
                p = (Message) nextField.get(p);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }


    public static class CustomHandler extends Handler {

        public CustomHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(Constant.LOG_TAG, "CustomHandler handleMessage: " + msg.what);
            if (msg.what == 0) {

                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }


        @Override
        public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
            Log.e(Constant.LOG_TAG, "CustomHandler sendMessageAtTime: " + msg + " , uptimeMillis: " + uptimeMillis);
            return super.sendMessageAtTime(msg, uptimeMillis);
        }
    }

}
