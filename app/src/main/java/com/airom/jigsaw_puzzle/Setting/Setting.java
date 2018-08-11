package com.airom.jigsaw_puzzle.Setting;

import android.os.Bundle;

import com.airom.jigsaw_puzzle.BaseActivity;
import com.airom.jigsaw_puzzle.R;

public class Setting extends BaseActivity {
    private String name;
    private int imageId;

    public Setting(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_item);
    }
}

