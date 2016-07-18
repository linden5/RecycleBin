package com.example.lyx.myapplication.ch07;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.lyx.myapplication.ch07.IPerson;

/**
 * Created by lyx on 2016/7/17.
 */
public class MyRemoteService extends Service {
    private IPerson.Stub iPerson = new IPersonImpl();
    @Override
    public IBinder onBind(Intent intent) {
        return iPerson;
    }
}
