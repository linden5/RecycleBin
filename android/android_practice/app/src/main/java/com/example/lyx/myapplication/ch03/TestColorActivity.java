package com.example.lyx.myapplication.ch03;

import android.app.Activity;
import android.os.Bundle;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/12.
 */
public class TestColorActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
        getWindow().setBackgroundDrawableResource(R.color.red_bg);
    }
}
