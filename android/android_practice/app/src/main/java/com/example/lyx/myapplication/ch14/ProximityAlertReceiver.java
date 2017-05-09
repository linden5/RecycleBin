package com.example.lyx.myapplication.ch14;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

/**
 * Created by lyx on 2016/7/24.
 */
public class ProximityAlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context,Intent intent) {
        String key = LocationManager.KEY_PROXIMITY_ENTERING;
        Boolean isEnter = intent.getBooleanExtra(key, false);
        if (isEnter) {
            Toast.makeText(context, "In", Toast.LENGTH_LONG).show();
        }
    }
}
