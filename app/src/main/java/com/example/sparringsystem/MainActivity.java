package com.example.sparringsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.sparringsystem.AudioUtilsModule.AudioUtilsFragment;
import com.example.sparringsystem.PracticeModule.MusicPlayerFragment;
import com.example.sparringsystem.PracticeModule.PracticeFragment;
import com.example.sparringsystem.UserModule.User;
import com.example.sparringsystem.UserModule.UserFragment;
import com.example.sparringsystem.musicPlayer.Song;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    // 子界面
    private HomeFragment homeFragment;
    private AudioUtilsFragment audioUtilsFragment;
    public PracticeFragment practiceFragment;
    private UserFragment userFragment;
    // 当前用户
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化类静态变量
        ImageSource.init();

        // 初始化一个用户
        currentUser = new User("爱音乐的Admin", "11111111111");
        currentUser.setAvatar(new ImageSource(R.drawable.r_4, "Administrator"));
        currentUser.setLevel(100);
        currentUser.setVipLevel(5);
        currentUser.setId("00000000");

        // 随机生成100个粉丝和关注
        for (int i = 0; i < 100; i++) {
            currentUser.addFans(new User(randomUserName(), randomPhone()));
            currentUser.addFollow(new User(randomUserName(), randomPhone()));
        }

        // 随机生成100首歌曲
        for (int i = 0; i < 100; i++) {
            Song song = new Song(R.raw.sample_song, randomUserName() + " Song", randomUserName() + " Artist");
            song.setImageResourceId(R.drawable.unknown);
            currentUser.addSongCollection(song);
        }

        // 初始化子界面
        homeFragment = new HomeFragment();
        audioUtilsFragment = new AudioUtilsFragment();
        practiceFragment = new PracticeFragment();
        userFragment = new UserFragment();

        userFragment.context = this;
        userFragment.currentUser = currentUser;

        // 加载底部导航栏
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // 默认加载HomeFragment
        loadFragment(new HomeFragment());
    }

    private String randomUserName() {
        Random random = new Random();
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            name.append((char) (random.nextInt(26) + 'a'));
        }
        return name.toString();
    }

    private String randomPhone() {
        Random random = new Random();
        StringBuilder phone = new StringBuilder("1");
        for (int i = 0; i < 10; i++) {
            phone.append(random.nextInt(10));
        }
        return phone.toString();
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
                    loadFragment(audioUtilsFragment);
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
        loadFragment(practiceFragment.songListFragment);
//        practiceFragment.navigationToSongListFragment(name);
    }

    public void navigationToMusicPlayerFragment(String name) {
        // 跳转到PracticeFragment的子界面MusicPlayerFragment
        loadFragment(practiceFragment.musicPlayerFragment);
//        practiceFragment.navigationToMusicPlayerFragment(name);
    }

    public void navigationToShouCangFragment(String name) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public User getCurrentUser() {
        return currentUser;
    }
}