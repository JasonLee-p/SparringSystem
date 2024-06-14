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

public class CategoryItemViewDoubleLine extends FrameLayout {

    private ImageView imageView;
    private TextView textView1;
    private TextView textView2;

    public CategoryItemViewDoubleLine(Context context) {
        super(context);
        init(context);
    }

    public CategoryItemViewDoubleLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CategoryItemViewDoubleLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_category_item_single, this, true);
        imageView = findViewById(R.id.category_image);
        textView1 = findViewById(R.id.category_text1);
        textView2 = findViewById(R.id.category_text2);
    }

    public void setCategory(String text1, String text2, Drawable image, OnClickListener onClickListener) {
        textView1.setText(text1);
        textView2.setText(text2);
        imageView.setImageDrawable(image);
        this.setOnClickListener(onClickListener);
    }
}
