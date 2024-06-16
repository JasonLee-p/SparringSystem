package com.example.sparringsystem.PracticeModule;

import com.example.sparringsystem.Course.Course;
import com.example.sparringsystem.musicPlayer.Song;

import java.util.Dictionary;

public class PracticeRecord {
    private Course course; // 隶属的课程
    private Song song; // 练习的歌曲
    private Dictionary<String, Integer> scoreRecord;
    private String videoPath;
    // 练习后给用户的建议
    private String suggestion;

    public PracticeRecord(Course course, Song song, Dictionary<String, Integer> scoreRecord, String videoPath, String suggestion) {
        this.course = course;
        this.song = song;
        this.scoreRecord = scoreRecord;
        this.videoPath = videoPath;
        this.suggestion = suggestion;
    }
}
