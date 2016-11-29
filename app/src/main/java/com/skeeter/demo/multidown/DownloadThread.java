package com.skeeter.demo.multidown;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author michael created on 2016/11/21.
 */
public class DownloadThread extends Thread {
    private long mStartPos;
    private long mTotalDownLength;
    private long mDownloadedLength = 0;

    private String mDownUrl;
    private String mTargetFilePath;

    private Runnable mDownSuccessCallback = null;

    public DownloadThread(String downUrl, String targetFilePath) {
        mDownUrl = downUrl;
        mTargetFilePath = targetFilePath;
    }

    public DownloadThread(String downUrl, String targetFilePath, long startPos, long totalDownLength) {
        mDownUrl = downUrl;
        mTargetFilePath = targetFilePath;
        mStartPos = startPos;
        mTotalDownLength = totalDownLength;
    }

    public DownloadThread setDownSuccessCallback(Runnable downSuccessCallback) {
        mDownSuccessCallback = downSuccessCallback;
        return this;
    }

    public long getDownloadedLength() {
        return mDownloadedLength;
    }

    @Override
    public void run() {
        RandomAccessFile file = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            file = new RandomAccessFile(mTargetFilePath, "rw");
            file.seek(mStartPos);

            URL url = new URL(mDownUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Range", "bytes=" + mStartPos + "-" + (mStartPos + mTotalDownLength));

            is = connection.getInputStream();

//            long skip = 0;
//            skip = is.skip(mStartPos);

            bis = new BufferedInputStream(is);

            // FIXME: 2016/11/21 没有生效
//            skip = bis.skip(mStartPos);
//            Log.e("download", "startpos: " + mStartPos + " skipped: " + skip);

            byte[] buffer = new byte[1024];
            int hasRead = 0;
            while (mDownloadedLength < mTotalDownLength && (hasRead = bis.read(buffer)) > 0) {
//            while ((hasRead = bis.read(buffer)) > 0) {
//                if (mDownloadedLength + hasRead <= mTotalDownLength) {
//                    file.write(buffer);
//                    mDownloadedLength += hasRead;
//                } else {
//                    file.write(buffer, 0, (int) (mTotalDownLength - mDownloadedLength));
//                    mDownloadedLength = mTotalDownLength;
//                }

                file.write(buffer, 0, hasRead);
                mDownloadedLength += hasRead;
            }


            Log.e("download", "hasRead: " + hasRead);
            if (mDownSuccessCallback != null) {
                mDownSuccessCallback.run();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(file);
            closeIO(bis);
            closeIO(is);
        }

    }

    public void closeIO(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
