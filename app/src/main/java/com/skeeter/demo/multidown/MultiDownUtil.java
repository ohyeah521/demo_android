package com.skeeter.demo.multidown;

import android.util.Log;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author michael created on 2016/11/21.
 */
public class MultiDownUtil {
    public static final int DEFAULT_MAX_THREAD_LENGTH = 10;
    public static final long DEFAULT_THREAD_DOWN_LENGTH = 1024 * 1024;

    private String mTargetFilePath;
    private String mDownUrl;
    private int mMaxThreadCount;
    private long mThreadDownLength;

    private DownloadThread[] mThreads;
    private long mTotalLength;
    private Timer mTimer;
    private TimerTask mTimerTask;

    private long mStartTime;

    public static MultiDownUtil newInstance(String targetFile, String downUrl) {
        return new MultiDownUtil(targetFile, downUrl);
    }

    public MultiDownUtil(String targetFile, String downUrl) {
        mTargetFilePath = targetFile;
        mDownUrl = downUrl;
        mMaxThreadCount = DEFAULT_MAX_THREAD_LENGTH;
        mThreadDownLength = DEFAULT_THREAD_DOWN_LENGTH;

        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (mTotalLength <= 0 || mThreads == null || mThreads.length <= 0) {
                    return;
                }

                long downloadedLength = 0;
                for (int i = 0; i < mThreads.length; i++) {
                    if (mThreads[i] != null) {
                        downloadedLength += mThreads[i].getDownloadedLength();
                    }
                }

                double percent = 100 * downloadedLength / (double) mTotalLength;
                Log.e("download",
                        String.format("has downloaded %.2f percent, %d/%d", percent, downloadedLength, mTotalLength));

                if (downloadedLength >= mTotalLength) {
                    Log.e("download_time", "all thread downloaded cost: " + (System.currentTimeMillis() - mStartTime));
                    mTimer.cancel();
                    mTimerTask.cancel();
                }
            }
        };
    }

    public MultiDownUtil setMaxThreadCount(int maxThreadCount) {
        if (mMaxThreadCount <= 0 || mMaxThreadCount > 10) {
            throw new IllegalArgumentException("maxThreadCount must >0 && <= " + DEFAULT_MAX_THREAD_LENGTH);
        }
        mMaxThreadCount = maxThreadCount;
        return this;
    }

    public MultiDownUtil setTargetFilePath(String targetFilePath) {
        mTargetFilePath = targetFilePath;
        return this;
    }

    public MultiDownUtil setThreadDownLength(long threadDownLength) {
        mThreadDownLength = threadDownLength;
        return this;
    }

    public MultiDownUtil setDownUrl(String downUrl) {
        mDownUrl = downUrl;
        return this;
    }

    public void download() {
        mStartTime = System.currentTimeMillis();

        RandomAccessFile file = null;
        try {
            URL url = new URL(mDownUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Accept", "*/*");
            connection.connect();

            mTotalLength = connection.getContentLength();
            connection.disconnect();
            Log.e("download", "totalLength is " + mTotalLength);
            long threadCount = mTotalLength / mThreadDownLength + 1;
            if (threadCount > mMaxThreadCount) {
                Log.e("download", " threadCount is " + threadCount + ", fixed it to maxThreadCount " + mMaxThreadCount);
                threadCount = mMaxThreadCount;
                mThreadDownLength = mTotalLength / threadCount;
            }

            Log.e("download", " threadCount is " + threadCount + ", threadDownLength is " + mThreadDownLength);
            mThreads = new DownloadThread[(int) threadCount];

            file = new RandomAccessFile(mTargetFilePath, "rw");
            file.setLength(mTotalLength);

            Log.e("download_time", "befor thread start cost: " + (System.currentTimeMillis() - mStartTime));

            for (int i = 0; i < mThreads.length; i++) {
                mThreads[i] = new DownloadThread(mDownUrl, mTargetFilePath, mThreadDownLength * i,
                        i == mThreads.length - 1 ? mTotalLength - mThreadDownLength * i : mThreadDownLength);
//                mThreads[i] = new DownloadThread(mDownUrl, mTargetFilePath, mThreadDownLength * i, mThreadDownLength);
                final int index = i;
                mThreads[i].setDownSuccessCallback(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("download", "thread index: " + index + " downloaded success.");
                        Log.e("download", "thread index: " + index + "download length: "
                                + mThreads[index].getDownloadedLength() + " download over: " +
                                (mThreads[index].getDownloadedLength() - mThreadDownLength));

                    }
                }).start();
            }

            Log.e("download_time", "after thread start cost: " + (System.currentTimeMillis() - mStartTime));

            mTimer.schedule(mTimerTask, 0, 1000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
