package com.ismos_salt_erp.utils;

import android.widget.ImageView;

import java.util.List;

public class AnimationTime {
    public static void animation(ImageView  v){
       v.animate().rotation(0f).setDuration(1000).start();

    }
}
