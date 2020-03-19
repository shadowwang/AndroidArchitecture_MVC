package com.example.androidarchitecture;

import android.app.Application;

public class DariesApplication extends Application {

    private static DariesApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static DariesApplication get() {
        return application;
    }
}
