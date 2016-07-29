package com.example.lyx.myapplication.ch13;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.lyx.myapplication.R;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by lyx on 2016/7/23.
 */
public class URLActivity extends Activity {
    private ImageView img;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_bitmap);
        img = (ImageView)findViewById(R.id.bitmapImageView01);
        String urlStr = "http://wap.bestpay.com.cn/assets/images/mob-images/loading-logo.png";

        try {
            URL url = new URL(urlStr);

            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();

            Bitmap bm = BitmapFactory.decodeStream(in);
            img.setImageBitmap(bm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
