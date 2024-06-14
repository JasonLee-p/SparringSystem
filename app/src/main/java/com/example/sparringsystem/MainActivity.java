package com.example.sparringsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.sparringsystem.AudioUtilsModule.AudioUtilsFragment;
import com.example.sparringsystem.PracticeModule.PracticeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    // 子界面
    private HomeFragment homeFragment;
    private AudioUtilsFragment tuningFragment;
    private PracticeFragment practiceFragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化子界面
        homeFragment = new HomeFragment();
        tuningFragment = new AudioUtilsFragment();
        practiceFragment = new PracticeFragment();
        userFragment = new UserFragment();

        // 加载底部导航栏
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // 默认加载HomeFragment
        loadFragment(new HomeFragment());
    }

    @Override
    public void onBackPressed() {
        // 如果在HomeFragment中按下返回键，退出应用
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
        super.onBackPressed();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            item -> {
                int active_item = item.getItemId();
                if (active_item == R.id.navigation_home) {
                    // 主页
                    loadFragment(homeFragment);
                    return true;
                } else if (active_item == R.id.navigation_tuning) {
                    // 调音界面
                    loadFragment(tuningFragment);
                    return true;
                } else if (active_item == R.id.navigation_practice) {
                    // 练习界面
                    loadFragment(practiceFragment);
                    return true;
                } else if (active_item == R.id.navigation_user) {
                    // 用户界面
                    loadFragment(userFragment);
                    return true;
                }
                return false;
            };

    void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        if (fragment instanceof HomeFragment) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void navigationToSongListFragment(String name) {
        // 跳转到PracticeFragment的子界面SongListFragment
        practiceFragment.navigationToSongListFragment(name);
    }

    public void navigationToMusicPlayerFragment(String name) {
        // 跳转到PracticeFragment的子界面MusicPlayerFragment
        practiceFragment.navigationToMusicPlayerFragment(name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}