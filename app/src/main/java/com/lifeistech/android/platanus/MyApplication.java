package com.lifeistech.android.platanus;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

/**
 * Created by MINAMI on 16/02/26.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
