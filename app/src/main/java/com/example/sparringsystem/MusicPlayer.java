package com.example.sparringsystem;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

public class MusicPlayer {
    private MediaPlayer mediaPlayer;
    private Context context;
    private boolean isPrepared = false;

    public MusicPlayer(Context context) {
        this.context = context;
    }

    public interface OnPreparedListener {
        void onPrepared();
    }

    public void playFromRaw(int resourceId, OnPreparedListener listener) {
        release();
        mediaPlayer = MediaPlayer.create(context, resourceId);
        if (mediaPlayer != null) {
            mediaPlayer.setOnPreparedListener(mp -> {
                isPrepared = true;
                mp.start();
                listener.onPrepared();
            });
            mediaPlayer.setOnCompletionListener(mp -> release());
        } else {
            Log.e("MusicPlayer", "Failed to create MediaPlayer");
        }
    }

    public void playFromUri(Uri uri, OnPreparedListener listener) {
        release();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.setOnPreparedListener(mp -> {
                isPrepared = true;
                mp.start();
                listener.onPrepared();
            });
            mediaPlayer.setOnCompletionListener(mp -> release());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("MusicPlayer", "Error setting data source", e);
        }
    }

    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            release();
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void resume() {
        if (mediaPlayer != null && isPrepared && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            isPrepared = false;
        }
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public int getDuration() {
        return mediaPlayer != null && isPrepared ? mediaPlayer.getDuration() : 0;
    }

    public int getCurrentPosition() {
        return mediaPlayer != null && isPrepared ? mediaPlayer.getCurrentPosition() : 0;
    }

    public void seekTo(int position) {
        if (mediaPlayer != null && isPrepared) {
            mediaPlayer.seekTo(position);
        }
    }
}
