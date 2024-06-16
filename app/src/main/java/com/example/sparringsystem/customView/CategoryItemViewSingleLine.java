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

public class CategoryItemViewSingleLine extends FrameLayout {

    private ImageView imageView;
    private TextView textView;

    public CategoryItemViewSingleLine(Context context) {
        super(context);
        init(context);
    }

    public CategoryItemViewSingleLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CategoryItemViewSingleLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_category_item_single, this, true);
        imageView = findViewById(R.id.category_user_image);
        textView = findViewById(R.id.category_user_name);
    }

    public void setCategory(String name, Drawable image, OnClickListener onClickListener) {
        textView.setText(name);
        imageView.setImageDrawable(image);
        this.setOnClickListener(onClickListener);
    }
}
