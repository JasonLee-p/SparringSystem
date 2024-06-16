package com.example.sparringsystem.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.sparringsystem.R;
import com.example.sparringsystem.musicPlayer.Song;

public class MusicItemView extends FrameLayout {

    private android.content.Context context;
    private ImageView imageView;
    private TextView songTitleView;
    private TextView songArtistView;

    public MusicItemView(Context context) {
        super(context);
        init(context);
    }

    public MusicItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MusicItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_music_item, this, true);
        imageView = findViewById(R.id.category_music_image);
        songTitleView = findViewById(R.id.category_music_name);
        songArtistView = findViewById(R.id.category_music_artist);
    }

    public void setCategory(Song song, OnClickListener onClickListener) {
        songTitleView.setText(song.getTitle());
        songArtistView.setText(song.getArtist());
        imageView.setImageDrawable(song.getImageDrawable(context));
        this.setOnClickListener(onClickListener);
    }
}
