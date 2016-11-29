package com.skeeter.demo.thread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.skeeter.demo.Constant;

import java.util.Arrays;

/**
 * @author michael created on 2016/11/21.
 */
public class CustomAsyncTask extends AsyncTask<String, Integer, String> {


    private Handler mHandler;

    public Handler getHandler() {
        return mHandler;
    }

    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.e(Constant.LOG_TAG, "CustomAsyncTask onPreExecute with thread: " + Thread.currentThread());
    }

    @Override
    protected String doInBackground(String... params) {
        Log.e(Constant.LOG_TAG, "CustomAsyncTask doInBackground with thread: " + Thread.currentThread());

        for (int i = 0; i < 100; i += 20) {
            publishProgress(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        StringBuilder builder = new StringBuilder();
        if (params != null) {
            for (String param : params) {
                builder.append(param).append(", ");
            }
        }

        return builder.toString();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.e(Constant.LOG_TAG,
                "CustomAsyncTask onProgressUpdate values: " + Arrays.asList(values).toString() +
                        " with thread: " + Thread.currentThread());
        if (mHandler != null) {
//            mHandler.sendEmptyMessage(values[0]);
            Message message = mHandler.obtainMessage(values[0]);
            message.sendToTarget();
        }


    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.e(Constant.LOG_TAG, "CustomAsyncTask onPostExecute result: " + s +
                "with thread: " + Thread.currentThread());
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.e(Constant.LOG_TAG, "CustomAsyncTask onCancelled with thread: " + Thread.currentThread());

    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        Log.e(Constant.LOG_TAG, "CustomAsyncTask onCancelled with result: " + s +
                " with thread: " + Thread.currentThread());
    }
}
