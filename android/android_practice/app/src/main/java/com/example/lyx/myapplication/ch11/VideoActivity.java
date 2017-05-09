package com.example.lyx.myapplication.ch11;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.widget.MediaController;
import android.os.Bundle;
import android.widget.VideoView;

import com.example.lyx.myapplication.R;

import java.io.File;

/**
 * Created by lyx on 2016/7/20.
 */
public class VideoActivity extends Activity {
    private VideoView video;
    private MediaController ctlr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.video);

        File clip = new File("/storage/extSdCard/原田ひとみ - emotional flutter.mp4");
        if (clip.exists()) {
            video = (VideoView)findViewById(R.id.video);
            video.setVideoPath(clip.getAbsolutePath());
            ctlr = new MediaController(this);
            video.setMediaController(ctlr);
            ctlr.setMediaPlayer(video);
            video.requestFocus();
        }
    }
}
