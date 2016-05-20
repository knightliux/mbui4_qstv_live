package com.moon.android.live.custom007;

import java.lang.Thread.UncaughtExceptionHandler;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class UncaughtException implements UncaughtExceptionHandler {
    private final static String TAG = "UncaughtException";
    private static UncaughtException mUncaughtException;
    private Context context;
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private UncaughtException() {
    }

    /**
     * ͬ�����������ⵥ����̻߳����³����쳣
     * 
     * @return
     */
    public synchronized static UncaughtException getInstance() {
        if (mUncaughtException == null) {
            mUncaughtException = new UncaughtException();
        }
        return mUncaughtException;
    }

    /**
     * ��ʼ�����ѵ�ǰ�������ó�UncaughtExceptionHandler������
     */
    public void init() {
        Thread.setDefaultUncaughtExceptionHandler(mUncaughtException);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
    	Log.e(TAG, "uncaughtException thread : " + thread + "||name=" + thread.getName() + "||id=" + thread.getId() + "||exception=" + ex);
    	Toast.makeText(getContext(), "error", 500).show();
    }

}
