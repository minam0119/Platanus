package com.lifeistech.android.platanus;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

/**
 * Created by MINAMI on 16/02/26.
 */
public class MyApplication extends Application {

    private static MyApplication singleInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        singleInstance = this;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public static synchronized MyApplication getInstance() {
        return singleInstance;
    }
}
