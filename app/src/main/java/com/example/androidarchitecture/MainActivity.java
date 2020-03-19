package com.example.androidarchitecture;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

/**
 * android架构demo——MVC
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化toolbar
        initToolbar();
        //初始化view
        initFragment();
    }

    private void initToolbar() {
        MaterialToolbar toolbar = (MaterialToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    private void initFragment() {
        DariesFragment dariesFragment = getDariesFragment();
        if (dariesFragment == null) {
            dariesFragment = new DariesFragment();
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), dariesFragment, R.id.content);
    }

    private DariesFragment getDariesFragment() {
        return (DariesFragment) getSupportFragmentManager().findFragmentById(R.id.content);
    }


}
