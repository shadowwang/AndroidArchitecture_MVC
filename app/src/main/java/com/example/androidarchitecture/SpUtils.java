package com.example.androidarchitecture;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.collection.SimpleArrayMap;

public class SpUtils {

    private static final SimpleArrayMap<String, SpUtils> mCaches = new SimpleArrayMap<>();

    private SharedPreferences mSharedPreferences;

    private SpUtils(String spName, int mode) {
        this.mSharedPreferences = DariesApplication.get().getSharedPreferences(spName, mode);
    }

    public static SpUtils getInstance(String spName) {
        SpUtils instance = mCaches.get(spName);
        if (instance == null) {
            instance = new SpUtils(spName, Context.MODE_PRIVATE);
            mCaches.put(spName, instance);
        }

        return instance;
    }

    public void put(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public String get(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public void remove(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }
}
