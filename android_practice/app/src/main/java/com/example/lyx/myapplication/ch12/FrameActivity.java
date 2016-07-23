package com.example.lyx.myapplication.ch12;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/21.
 */
public class FrameActivity extends Activity {
    private Button b1, b2;
    private ImageView img;
    private AnimationDrawable dance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tween_anime);
        img = (ImageView)findViewById(R.id.ImageView01);
        b1 = (Button)findViewById(R.id.Button01);
        b2 = (Button)findViewById(R.id.Button02);

        dance = (AnimationDrawable)img.getBackground();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dance.start();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dance.stop();
            }
        });
    }
}
