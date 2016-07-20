package com.example.lyx.myapplication.ch08;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by lyx on 2016/7/18.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent intent) {
        String msg = intent.getStringExtra("msg");
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();

        Intent i = new Intent();
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setClass(ctx, NotifActivity.class);
        ctx.startActivity(i);
    }
}
