package musicPlayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

public class MusicPlayer {
    private MediaPlayer mediaPlayer;
    private Context context;
    private boolean isPrepared = false;

    // 播放列表
    public SongList songList = new SongList();

    public MusicPlayer(Context context) {
        this.context = context;
    }

    public interface OnPreparedListener {
        void onPrepared();
    }

    public interface OnSongChangedListener {
        void onSongChanged(Song newSong);
    }

    private OnSongChangedListener songChangedListener;

    public void setOnSongChangedListener(OnSongChangedListener listener) {
        this.songChangedListener = listener;
    }

    public boolean startPlayingCurrentSong(OnPreparedListener listener) {
        if (songList.getCurrentSong() == null) {
            Log.e("MusicPlayer", "No song in the list");
            return false;
        }

        Song currentSong = songList.getCurrentSong();
        boolean success = false;
        if (currentSong.isUrl()) {
            success = startPlayFromUri(Uri.parse(currentSong.getUrl()), listener);
        } else if (currentSong.isLocalPath()) {
            success = startPlayFromLocalPath(currentSong.getLocalPath(), listener);
        } else if (currentSong.isResourceId()) {
            success = playFromRaw(currentSong.getResourceId(), listener);
        }

        if (songChangedListener != null) {
            songChangedListener.onSongChanged(currentSong);
        }
        return success;
    }

    private boolean playFromRaw(int resourceId, OnPreparedListener listener) {
        release();
        mediaPlayer = MediaPlayer.create(context, resourceId);
        if (mediaPlayer != null) {
            mediaPlayer.setOnPreparedListener(mp -> {
                isPrepared = true;
                mp.start();
                listener.onPrepared();
            });
            mediaPlayer.setOnCompletionListener(mp -> onSongCompletion(listener));
        } else {
            Log.e("MusicPlayer", "Failed to create MediaPlayer");
            return false;
        }
        return true;
    }

    private boolean startPlayFromUri(Uri uri, OnPreparedListener listener) {
        release();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.setOnPreparedListener(mp -> {
                isPrepared = true;
                mp.start();
                listener.onPrepared();
            });
            mediaPlayer.setOnCompletionListener(mp -> onSongCompletion(listener));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("MusicPlayer", "Error setting data source", e);
            return false;
        }
        return true;
    }

    private boolean startPlayFromLocalPath(String localPath, OnPreparedListener listener) {
        release();
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(localPath);
            mediaPlayer.setOnPreparedListener(mp -> {
                isPrepared = true;
                mp.start();
                listener.onPrepared();
            });
            mediaPlayer.setOnCompletionListener(mp -> onSongCompletion(listener));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("MusicPlayer", "Error setting data source", e);
            return false;
        }
        return true;
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

    public int getDuration() {
        return mediaPlayer != null && isPrepared ? mediaPlayer.getDuration() : 0;
    }

    public int getCurrentPosition() {
        // 返回当前播放位置
        return mediaPlayer != null && isPrepared ? mediaPlayer.getCurrentPosition() : 0;
    }

    public boolean isPlaying() {
        // 返回是否正在播放
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public void seekTo(int position) {
        if (mediaPlayer != null && isPrepared) {
            mediaPlayer.seekTo(position);
        }
    }

    public boolean playNext(OnPreparedListener listener) {
        songList.getNextSong();
        return startPlayingCurrentSong(listener);
    }

    public boolean playPrevious(OnPreparedListener listener) {
        songList.getPreviousSong();
        return startPlayingCurrentSong(listener);
    }

    private boolean onSongCompletion(OnPreparedListener listener) {
        // Automatically play the next song when the current song completes
        return playNext(listener);
    }
}
