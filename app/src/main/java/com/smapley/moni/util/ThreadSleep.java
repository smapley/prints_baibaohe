package com.smapley.moni.util;

import android.os.Handler;
import android.os.Message;

/**
 * Created by smapley on 15/11/18.
 */
public class ThreadSleep {

    private final int SLEEP = 1;
    private boolean mIsLoops = false;
    private Callback mcallback;
    private int mNumber = 0;


    public ThreadSleep() {

    }

    public ThreadSleep isLoop() {
        this.mIsLoops = true ;
        return this;
    }

    public void stop(){
        this.mIsLoops=false;
    }

    public void sleep(final long time, Callback callback) {
        mcallback = callback;
        mNumber = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mhandler.obtainMessage(SLEEP).sendToTarget();
                    if (!mIsLoops)
                        return;
                }
            }
        }).start();
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SLEEP:
                    mNumber++;
                    mcallback.onCallback(ThreadSleep.this,mNumber);
                    break;
            }
        }
    };

    public interface Callback {
        void onCallback(ThreadSleep threadSleep, int number);
    }
}
