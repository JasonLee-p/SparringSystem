package com.example.sparringsystem.customView;

import com.example.sparringsystem.ImageSource;

public class RecommendationItem {
    private ImageSource imageSource;
    private String title;

    RecommendationItem(ImageSource imageSource, String title) {
        this.imageSource = imageSource;
        this.title = title;
    }
}
