package com.example.lyx.myapplication.ch07;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by lyx on 2016/7/17.
 */
public class MyService extends Service {
    public IBinder onBind(Intent intent) {
        Log.i("SERVICE", "Onbind");
        Toast.makeText(MyService.this, "Onbind", Toast.LENGTH_LONG).show();
        return null;
    }

    public void onCreate() {
        Log.i("SERVICE", "OnCreate");
        Toast.makeText(MyService.this, "OnCreate", Toast.LENGTH_LONG).show();
    }

    public void onStart(Intent intent, int startId) {
        Log.i("SERVICE", "OnStart");
        Toast.makeText(MyService.this, "OnStart", Toast.LENGTH_LONG).show();
    }

    public void onDestroy() {
        Log.i("SERVICE", "OnDestroy");
        Toast.makeText(MyService.this, "OnDestroy", Toast.LENGTH_LONG).show();
    }
}
