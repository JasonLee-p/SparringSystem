package com.example.sparringsystem.UserModule;

public class User {
    private String nickname; // 用户昵称
    private String id; // 用户id
    private int level; // 用户等级
    private String birthday; // 用户生日
    private int followCount; // 用户关注数量

    // 构造函数
    public User(String nickname, String id, int level, String birthday, int followCount) {
        this.nickname = nickname;
        this.id = id;
        this.level = level;
        this.birthday = birthday;
        this.followCount = followCount;
    }

    // getter 和 setter 方法
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }
}
