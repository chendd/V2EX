package com.chendd.v2ex;

import android.app.Application;
import android.content.Context;

import com.chendd.v2ex.utils.AppContextUtil;

/**
 * @author admin
 * @Time 2016/6/21.
 */
public class App extends Application {

    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = this;
        AppContextUtil.init(this);
    }

    // 获取ApplicationContext
    public static Context getContext() {
        return mApplicationContext;
    }
}
