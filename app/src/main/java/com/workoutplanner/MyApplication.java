package com.workoutplanner;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

public class MyApplication extends Application {

    private static WeakReference<Context> context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = new WeakReference<>(getApplicationContext());
    }

    public static Context getAppContext() {
        return MyApplication.context.get();
    }
}
