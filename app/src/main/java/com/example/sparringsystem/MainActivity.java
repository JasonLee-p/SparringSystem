package com.example.sparringsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 加载底部导航栏
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            item -> {
                int _id = item.getItemId();
                if (_id == R.id.navigation_home) {
                    // 主页
                    return true;
                } else if (_id == R.id.navigation_music) {
                    // 音乐
                    return true;
                } else if (_id == R.id.navigation_notifications) {
                    // 通知
                    return true;
                }
                return false;
            };
}