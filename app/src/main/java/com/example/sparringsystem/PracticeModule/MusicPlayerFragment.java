package com.example.sparringsystem.PracticeModule;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sparringsystem.R;

import java.util.concurrent.TimeUnit;

import com.example.sparringsystem.musicPlayer.MusicPlayer;
import com.example.sparringsystem.musicPlayer.Song;

public class MusicPlayerFragment extends Fragment {

    private static final String TAG = "MusicPlayerFragment";

    private MusicPlayer musicPlayer;
    private Handler handler = new Handler();
    private SeekBar seekBar;
    private TextView currentTime, totalTime, lyricsView, songTitle, songArtist;
    private ImageView albumImage;
    private Runnable updateSeekBar;

    public MusicPlayerFragment() {
        // Required empty public constructor
    }

    public static MusicPlayerFragment newInstance(String param1, String param2) {
        MusicPlayerFragment fragment = new MusicPlayerFragment();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString("param1");
            String mParam2 = getArguments().getString("param2");
        }
        musicPlayer = new MusicPlayer(getActivity());
        musicPlayer.setOnSongChangedListener(this::updateSongInfo);
        // Example of adding songs to the list
        musicPlayer.songList.addSong(new Song(R.raw.sample_song, "Sample Song", "Unknown Artist"));
        musicPlayer.songList.addSong(new Song("https://www.example.com/sample.mp3", "Sample Song 2", "Unknown Artist"));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Inflating layout for MusicPlayerFragment");
        return inflater.inflate(R.layout.fragment_music_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: View created for MusicPlayerFragment");

        try {
            ImageButton playPauseButton = view.findViewById(R.id.button_play_pause);
            ImageButton previousButton = view.findViewById(R.id.button_previous);
            ImageButton nextButton = view.findViewById(R.id.button_next);
            seekBar = view.findViewById(R.id.seekBar);
            currentTime = view.findViewById(R.id.current_time);
            totalTime = view.findViewById(R.id.total_time);
            lyricsView = view.findViewById(R.id.lyrics);
            songTitle = view.findViewById(R.id.song_title);
            songArtist = view.findViewById(R.id.song_artist);
            albumImage = view.findViewById(R.id.album_image);

            if (playPauseButton == null || previousButton == null || nextButton == null || seekBar == null || currentTime == null || totalTime == null || lyricsView == null || songTitle == null || songArtist == null || albumImage == null) {
                Log.e(TAG, "多个控件是 null，无法初始化");
                return;
            }

            // 播放、暂停
            playPauseButton.setOnClickListener(v -> {
                if (musicPlayer.isPlaying()) {
                    musicPlayer.pause();
                    playPauseButton.setImageResource(R.drawable.ic_play);
                } else {
                    boolean success;
                    // 如果没有在播放，就播放
                    if (musicPlayer.getCurrentPosition() == 0) {
                        success = musicPlayer.startPlayingCurrentSong(this::updateSeekBar);
                    } else {
                        musicPlayer.resume();
                        success = true;
                    }
                    if (success) playPauseButton.setImageResource(R.drawable.ic_pause);
                }
            });

            // 上一首
            previousButton.setOnClickListener(v -> {
                // 更新进度条总时间
                resetSeekBarAndTextTime();
                if (musicPlayer.playPrevious(this::updateSeekBar)) {
                    playPauseButton.setImageResource(R.drawable.ic_pause);
                } else {
                    playPauseButton.setImageResource(R.drawable.ic_play);
                }
                totalTime.setText(formatTime(musicPlayer.getDuration()));
            });

            // 下一首
            nextButton.setOnClickListener(v -> {
                // 更新进度条总时间
                resetSeekBarAndTextTime();
                if (musicPlayer.playNext(this::updateSeekBar)) {
                    playPauseButton.setImageResource(R.drawable.ic_pause);
                } else {
                    playPauseButton.setImageResource(R.drawable.ic_play);
                }
            });

            // 拖动进度条
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        musicPlayer.seekTo(progress);
                        currentTime.setText(formatTime(progress));
                        totalTime.setText(formatTime(musicPlayer.getDuration()));
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            });

            // Reset the seek bar and text time when the fragment is recreated
            resetSeekBarAndTextTime();
            loadSongInfo();
        } catch (Exception e) {
            Log.e(TAG, "Error initializing views or setting up listeners", e);
        }
    }

    private void resetSeekBarAndTextTime() {
        // 重置进度条和时间，设置为0
        seekBar.setProgress(0);
        currentTime.setText("00:00");
        totalTime.setText(formatTime(musicPlayer.getDuration()));
    }

    private void updateSeekBar() {
        if (musicPlayer == null) {
            Log.e(TAG, "Music player 是 null");
            return;
        }
        if (seekBar == null) {
            Log.e(TAG, "Seek bar 是 null");
            return;
        }
        seekBar.setMax(musicPlayer.getDuration());
        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                int currentPosition = musicPlayer.getCurrentPosition();
                seekBar.setProgress(currentPosition);
                currentTime.setText(formatTime(currentPosition));
                totalTime.setText(formatTime(musicPlayer.getDuration()));
                if (musicPlayer.isPlaying()) {
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.post(updateSeekBar);
    }

    private String formatTime(int milliseconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void loadSongInfo() {
        Song currentSong = musicPlayer.songList.getCurrentSong();
        if (currentSong != null) {
            updateSongInfo(currentSong);
            seekBar.setMax(musicPlayer.getDuration());
            totalTime.setText(formatTime(musicPlayer.getDuration()));
        }
    }

    private void updateSongInfo(Song song) {
        songTitle.setText(song.getTitle());
        songArtist.setText(song.getArtist());
        int img_id = song.getImageResourceId();
        if (img_id != 0) {
            albumImage.setImageResource(img_id);
        } else {
            albumImage.setImageResource(R.drawable.unknown_album);
        }
        lyricsView.setText("无歌词"); // 这里可以更新歌词
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: Removing updateSeekBar callbacks");
        handler.removeCallbacks(updateSeekBar);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: Updating seek bar");
        updateSeekBar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (musicPlayer != null) {
            musicPlayer.release();
        }
        handler.removeCallbacks(updateSeekBar);
    }
}
