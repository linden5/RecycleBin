package com.example.lyx.myapplication.ch11;

import android.app.Activity;
import android.app.AlertDialog;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;

import com.example.lyx.myapplication.R;
import com.example.lyx.myapplication.ch10.Employees;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;

/**
 * Created by lyx on 2016/7/20.
 */
public class RecordActivity extends Activity {
    private ImageButton record, stop;
    private MediaRecorder mr;
    private String path;
    private File file;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_player);
        record = (ImageButton)findViewById(R.id.play);
        stop = (ImageButton)findViewById(R.id.stop);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                record();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
            }
        });
        setup();
    }

    private void setPath() throws IOException {
        path = "/storage/extSdCard/test.m4a";
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            throw new IOException("No sdcard:" + state);
        }

        File dir =  new File(path).getParentFile();
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IOException("File not able to create");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (stop.isEnabled()) {
            stop();
        }
    }

    private void record() {
        try {
            mr.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mr.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        record.setEnabled(false);
        stop.setEnabled(true);
    }

    private void stop() {
        mr.stop();
        stop.setEnabled(false);
        record.setEnabled(true);
        mr.release();
    }

    private void setProperty() {
        try {
            mr = new MediaRecorder();
            mr.setAudioSource(MediaRecorder.AudioSource.MIC);
            mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                setPath();
            } catch (Exception e) {
                e.printStackTrace();
            }

            mr.setOutputFile(path);
        } catch (Throwable t) {
            error(t);
        }
    }

    private void setup() {
        setProperty();
        record.setEnabled(true);
        stop.setEnabled(false);
    }

    private void error(Throwable t) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("error").setMessage(t.toString()).setPositiveButton("Ok", null).show();
    }
}
