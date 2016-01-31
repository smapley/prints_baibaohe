package com.smapley.moni;

import android.app.Application;

import org.xutils.x;

/**
 * Created by smapley on 16/1/31.
 */
public class LocalApplication extends Application {
    private static LocalApplication instance;



    //单例模式中获取唯一的MyApplication实例
    public static LocalApplication getInstance() {
        if (instance == null) {
            instance = new LocalApplication();
        }

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化xUtils
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);

        instance = this;
    }
}
