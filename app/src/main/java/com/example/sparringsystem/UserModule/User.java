package com.example.sparringsystem.UserModule;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;

import com.example.sparringsystem.Course.Course;
import com.example.sparringsystem.ImageSource;
import com.example.sparringsystem.PracticeModule.PracticeRecord;
import com.example.sparringsystem.musicPlayer.Song;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userName = "游客"; // 用户昵称
    private String phone = "11111111111"; // 用户手机号
    private String id = "00000000"; // 用户id
    private String password;
    private ImageSource avatar = ImageSource.UNKNOWN; // 用户头像
    private int vipLevel = 0; // 用户vip等级
    private int level = 0; // 用户等级
    private int[] birthday = new int[]{1990, 1, 1}; // 用户生日
    ArrayList<User> followList = new ArrayList<>(); // 用户关注列表
    ArrayList<User> fansList = new ArrayList<>(); // 用户粉丝列表
    ArrayList<Song> songCollection = new ArrayList<>(); // 用户收藏的歌曲列表
    ArrayList<Course> courseCollection = new ArrayList<>(); // 用户收藏的课程列表
    ArrayList<PracticeRecord> practiceRecord = new ArrayList<>(); // 用户练习记录
    ArrayList<String> loginRecord = new ArrayList<>(); // 用户登录记录

    // 构造函数
    public User(String userName, String phone) { this.userName = userName; this.phone = phone; }
    public User(String userName, int phone) { this.userName = userName; this.phone = String.valueOf(phone); }
    public User(String userName) { this.userName = userName; }
    public User(int phone) { this.phone = String.valueOf(phone); }

    // 获取属性
    public String getUserName() { return userName; }
    public String getPhone() { return phone; }
    public String getId() { return id; }
    public int getVipLevel() { return vipLevel; }
    public int getLevel() { return level; }
    public int[] getBirthday() { return birthday; }
    public ArrayList<User> getFollowList() { return followList; }
    public ArrayList<User> getFansList() { return fansList; }
    public ArrayList<Song> getSongCollection() { return songCollection; }
    public ArrayList<Course> getCourseCollection() { return courseCollection; }
    public ArrayList<PracticeRecord> getPracticeRecord() { return practiceRecord; }
    public ArrayList<String> getLoginRecord() { return loginRecord; }

    // 设置属性
    public void setUserName(String userName) { this.userName = userName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setId(String id) { this.id = id; }
    public void setAvatar(ImageSource avatar) { this.avatar = avatar; }
    public void setVipLevel(int vipLevel) { this.vipLevel = vipLevel; }
    public void setLevel(int level) { this.level = level; }
    public void setBirthday(int[] birthday) { this.birthday = birthday; }
    public void setBirthday(int year, int month, int day) { this.birthday = new int[]{year, month, day}; }
    public void setBirthdayYear(int year) { this.birthday[0] = year; }
    public void setBirthdayMonth(int month) { this.birthday[1] = month; }
    public void setBirthdayDay(int day) { this.birthday[2] = day; }

    // 获取图标
    public int getAvatarId() { return avatar.getResourceId(); }

    // 添加记录
    public void addFollow(User user) { followList.add(user); }
    public void addFans(User user) { fansList.add(user); }
    public void addSongCollection(Song song) { songCollection.add(song); }
    public void addCourseCollection(Course course) { courseCollection.add(course); }
    public void addPracticeRecord(PracticeRecord record) { practiceRecord.add(record); }
    public void addLoginRecord(String record) { loginRecord.add(record); }

    // 删除记录
    public void removeFollow(User user) { followList.remove(user); }
    public void removeFans(User user) { fansList.remove(user); }
    public void removeSongCollection(Song song) { songCollection.remove(song); }
    public void removeCourseCollection(Course course) { courseCollection.remove(course); }
    public void removePracticeRecord(PracticeRecord record) { practiceRecord.remove(record); }
    public void removeLoginRecord(String record) { loginRecord.remove(record); }

    // 升级
    public void upGrade() { level++; }
    public void upVipLevel() { vipLevel++; }

    // 验证
    public boolean checkPassword(String password) {
        // 以后要改成服务器端验证
        return this.password.equals(password);
    }

    public Drawable getAvatarDrawable(Context context) {
        return avatar.getImageDrawable(context);
    }
}
