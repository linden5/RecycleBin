package com.example.lyx.myapplication.ch12;

import android.util.Xml;
import android.view.animation.AnimationUtils;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/21.
 */
public class XmlAnimationActivity extends TweenActivity {
    @Override
    protected void initAnimation() {
        scaleAnimation = AnimationUtils.loadAnimation(XmlAnimationActivity.this, R.anim.scale);
        translateAnimation = AnimationUtils.loadAnimation(XmlAnimationActivity.this, R.anim.translate);
        alphaAnimation = AnimationUtils.loadAnimation(XmlAnimationActivity.this, R.anim.alpha);
        rotateAnimation = AnimationUtils.loadAnimation(XmlAnimationActivity.this, R.anim.rotate);
    }
}
