package com.example.lyx.myapplication.ch12;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/21.
 */
public class TweenActivity extends Activity {
    protected Button b1, b2, b3, b4;
    protected ImageView img;
    protected Animation alphaAnimation, scaleAnimation, translateAnimation, rotateAnimation;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tween_anime);
        img = (ImageView)findViewById(R.id.ImageView01);
        b1 = (Button)findViewById(R.id.Button01);
        b2 = (Button)findViewById(R.id.Button02);
        b3 = (Button)findViewById(R.id.Button03);
        b4 = (Button)findViewById(R.id.Button04);
        initAnimation();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img.startAnimation(scaleAnimation);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img.startAnimation(alphaAnimation);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img.startAnimation(translateAnimation);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img.setAnimation(rotateAnimation);
            }
        });
    }

    protected void initAnimation() {
        scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        scaleAnimation.setDuration(3000);

        rotateAnimation = new RotateAnimation(0f, +360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(3000);

        translateAnimation = new TranslateAnimation(10, 100, 10, 100);
        translateAnimation.setDuration(3000);

        alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(3000);
    }
}
