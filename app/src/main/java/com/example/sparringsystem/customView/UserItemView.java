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
import com.example.sparringsystem.UserModule.User;

public class UserItemView extends FrameLayout {

    private android.content.Context context;
    private ImageView imageView;
    private TextView userNameView;
    private TextView userLevelView;

    public UserItemView(Context context) {
        super(context);
        init(context);
    }

    public UserItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UserItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_user_item, this, true);
        imageView = findViewById(R.id.category_user_image);
        userNameView = findViewById(R.id.category_user_name);
        userLevelView = findViewById(R.id.category_user_level);
    }

    public void setCategory(User user, OnClickListener onClickListener) {
        userNameView.setText("用户名：" + user.getUserName());
        userLevelView.setText("等级：" + user.getLevel());
        imageView.setImageDrawable(user.getAvatarDrawable(context));
        this.setOnClickListener(onClickListener);
    }
}
