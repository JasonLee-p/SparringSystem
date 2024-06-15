package com.example.sparringsystem.customView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.sparringsystem.R;

public class MusicItemView extends FrameLayout {

    private ImageView imageView;
    private TextView textView;

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
        LayoutInflater.from(context).inflate(R.layout.view_music_item, this, true);
        imageView = findViewById(R.id.category_single_image);
        textView = findViewById(R.id.category_name);
    }

    public void setCategory(String musicName, Drawable image, OnClickListener onClickListener) {
        textView.setText(musicName);
        imageView.setImageDrawable(image);
        this.setOnClickListener(onClickListener);
    }
}
