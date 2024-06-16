package com.example.sparringsystem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class ImageSource {
    public static ImageSource RECOMMENDATION1;
    public static ImageSource RECOMMENDATION2;
    public static ImageSource RECOMMENDATION3;
    public static ImageSource RECOMMENDATION4;
    public static ImageSource UNKNOWN;

    private String url = null;
    private String localPath = null;
    private int resourceId = 0;
    private String title;

    public static void init() {
        RECOMMENDATION1 = new ImageSource(R.drawable.r_1, "推荐1");
        RECOMMENDATION2 = new ImageSource(R.drawable.r_2, "推荐2");
        RECOMMENDATION3 = new ImageSource(R.drawable.r_3, "推荐3");
        RECOMMENDATION4 = new ImageSource(R.drawable.r_4, "推荐4");
        UNKNOWN = new ImageSource(R.drawable.unknown, "未知");
    }


    public ImageSource(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public ImageSource(int resourceId, String title) {
        this.resourceId = resourceId;
        this.title = title;
    }

    public ImageSource(String localPath, String title, boolean isLocal) {
        this.localPath = localPath;
        this.title = title;
    }

    public String getUrl() {
        if (url == null) return "";
        return url;
    }

    public String getLocalPath() {
        if (localPath == null) return "";
        return localPath;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResourceId() {
        if (resourceId == 0) return R.drawable.unknown;
        return resourceId;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public Drawable getImageDrawable(Context context) {
        Resources.Theme theme = context.getTheme();
        if (resourceId != 0) {
            return context.getResources().getDrawable(resourceId, theme);
        } else if (localPath != null) {
            return Drawable.createFromPath(localPath);
        } else if (url != null) {
            return context.getResources().getDrawable(R.drawable.unknown, theme);
        } else {
            return context.getResources().getDrawable(R.drawable.unknown, theme);
        }
    }

    public boolean isUrl() {
        return url != null;
    }

    public boolean isLocalPath() {
        return localPath != null;
    }

    public boolean isResourceId() {
        return resourceId != 0;
    }

    public void setTitle(String title) { this.title = title; }
}
