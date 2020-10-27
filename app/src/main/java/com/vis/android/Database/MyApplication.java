package com.vis.android.Database;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by Faiz on 6/18/2017.
 */

public class MyApplication extends Application {

    private static MyApplication mainApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mainApplication = this;
        DatabaseController.openDataBase(this);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        //MultiDex.install(this);
    }


    public static synchronized MyApplication getInstance() {
        return mainApplication;
    }
}
