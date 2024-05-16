package com.example.sparringsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
        // 默认加载HomeFragment
        loadFragment(new HomeFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            item -> {
                int active_item = item.getItemId();
                if (active_item == R.id.navigation_home) {
                    // 主页
                    loadFragment(new HomeFragment());
                    return true;
                } else if (active_item == R.id.navigation_tuning) {
                    // 调音界面
                    loadFragment(new TuningFragment());
                    return true;
                } else if (active_item == R.id.navigation_music) {
                    // 练习界面
                    loadFragment(new PracticeFragment());
                    return true;
                } else if (active_item == R.id.navigation_notifications) {
                    // 通知
                    loadFragment(new NavigationFragment());
                    return true;
                }
                return false;
            };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}