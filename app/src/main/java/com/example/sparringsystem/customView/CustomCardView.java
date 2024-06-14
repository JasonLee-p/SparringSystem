package com.example.sparringsystem.customView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.cardview.widget.CardView;
import android.widget.ImageView;
import com.example.sparringsystem.R;

public class CustomCardView extends CardView {

    private ImageView imageView;

    public CustomCardView(Context context) {
        super(context);
        init(context);
    }

    public CustomCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_custom_card, this, true);
        imageView = findViewById(R.id.imageView);
    }

    public void setCategory(String name, Drawable image, OnClickListener onClickListener) {
        imageView.setImageDrawable(image);
        this.setOnClickListener(onClickListener);
    }
}
