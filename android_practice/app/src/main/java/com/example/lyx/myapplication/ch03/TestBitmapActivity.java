package com.example.lyx.myapplication.ch03;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/14.
 */
public class TestBitmapActivity extends Activity {
    private ImageView myImageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_bitmap);
        myImageView = (ImageView)findViewById(R.id.bitmapImageView02);
        Resources r = getResources();
        Drawable d = r.getDrawable(R.drawable.player);
        myImageView.setImageDrawable(d);
    }
}
