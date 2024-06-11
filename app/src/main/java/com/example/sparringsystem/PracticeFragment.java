package com.example.sparringsystem;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.TimeUnit;

public class PracticeFragment extends Fragment {

    private MusicPlayer musicPlayer;
    private Handler handler = new Handler();
    private SeekBar seekBar;
    private TextView textTime;
    private Runnable updateSeekBar;

    public PracticeFragment() {
        // Required empty public constructor
    }

    public static PracticeFragment newInstance(String param1, String param2) {
        PracticeFragment fragment = new PracticeFragment();
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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_practice, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button playButton = view.findViewById(R.id.button_play);
        Button pauseButton = view.findViewById(R.id.button_pause);
        Button replayButton = view.findViewById(R.id.button_replay);
        seekBar = view.findViewById(R.id.seekBar);
        textTime = view.findViewById(R.id.text_time);

        playButton.setOnClickListener(v -> {
            musicPlayer.playFromRaw(R.raw.sample, this::updateSeekBar);
        });

        pauseButton.setOnClickListener(v -> musicPlayer.pause());

        replayButton.setOnClickListener(v -> {
            musicPlayer.stop();
            musicPlayer.playFromRaw(R.raw.sample, this::updateSeekBar);
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    musicPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private void updateSeekBar() {
        seekBar.setMax(musicPlayer.getDuration());
        updateSeekBar = new Runnable() {
            @Override
            public void run() {
                int currentPosition = musicPlayer.getCurrentPosition();
                seekBar.setProgress(currentPosition);
                textTime.setText(formatTime(currentPosition) + "/" + formatTime(musicPlayer.getDuration()));
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (musicPlayer != null) {
            musicPlayer.release();
        }
        handler.removeCallbacks(updateSeekBar);
    }
}
