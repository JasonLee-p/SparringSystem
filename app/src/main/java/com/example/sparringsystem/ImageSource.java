package com.example.sparringsystem;

import android.media.Image;

public class ImageSource {
    public static final ImageSource RECOMMENDATION1 = new ImageSource(R.drawable.r1, "推荐1");
    public static final ImageSource RECOMMENDATION2 = new ImageSource(R.drawable.r2, "推荐2");
    public static final ImageSource RECOMMENDATION3 = new ImageSource(R.drawable.r3, "推荐3");
    public static final ImageSource RECOMMENDATION4 = new ImageSource(R.drawable.r4, "推荐4");

    private String url = null;
    private String localPath = null;
    private int resourceId = 0;
    private String title;
    private int imageResourceId;


    public ImageSource(String url, String title, String description) {
        this.url = url;
        this.title = title;
    }

    public ImageSource(int resourceId, String title) {
        this.resourceId = resourceId;
        this.title = title;
    }

    public ImageSource(String localPath, String title, int imageResourceId, boolean isLocal) {
        this.localPath = localPath;
        this.title = title;
        this.imageResourceId = imageResourceId;
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
        return imageResourceId;
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
}
