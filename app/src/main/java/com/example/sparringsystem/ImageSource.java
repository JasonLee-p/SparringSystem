package com.example.sparringsystem;

public class ImageSource {
    public static ImageSource RECOMMENDATION1;
    public static ImageSource RECOMMENDATION2;
    public static ImageSource RECOMMENDATION3;
    public static ImageSource RECOMMENDATION4;

    private String url = null;
    private String localPath = null;
    private int resourceId = 0;
    private String title = "";

    public static void init() {
        RECOMMENDATION1 = new ImageSource(R.drawable.r_1, "推荐1");
        RECOMMENDATION2 = new ImageSource(R.drawable.r_2, "推荐2");
        RECOMMENDATION3 = new ImageSource(R.drawable.r_3, "推荐3");
        RECOMMENDATION4 = new ImageSource(R.drawable.r_4, "推荐4");
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
