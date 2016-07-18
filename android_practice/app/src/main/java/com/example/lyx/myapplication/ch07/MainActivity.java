package com.example.lyx.myapplication.ch07;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/17.
 */
public class MainActivity extends Activity {
    public static final String MY_SERVICE = "com.example.lyx.myapplication.ch07.MY_SERVICE";
    private Button startBtn, stopBtn, bindBtn, unbindBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.service);
        startBtn = (Button)findViewById(R.id.startButton01);
        stopBtn = (Button)findViewById(R.id.stopButton02);
        bindBtn = (Button)findViewById(R.id.bindButton03);
        unbindBtn = (Button)findViewById(R.id.unbindButton04);

        startBtn.setOnClickListener(startListener);
        stopBtn.setOnClickListener(stopListener);
        bindBtn.setOnClickListener(bindListener);
        unbindBtn.setOnClickListener(unbindListener);
    }

    private View.OnClickListener startListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setAction(MY_SERVICE);
            startService(intent);
        }
    };

    private View.OnClickListener stopListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setAction(MY_SERVICE);
            stopService(intent);
        }
    };

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("SERVICE", "Linked!");
            Toast.makeText(MainActivity.this, "Linked!", Toast.LENGTH_LONG).show();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("SERVICE", "Discennected!");
            Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_LONG).show();
        }
    };

    private View.OnClickListener bindListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setAction(MY_SERVICE);
            bindService(intent, conn, Service.BIND_AUTO_CREATE);
        }
    };

    private View.OnClickListener unbindListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            unbindService(conn);
        }
    };
}
