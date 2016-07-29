package com.example.lyx.myapplication.ch14;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/24.
 */
public class AlertActivity extends Activity {
    private static String PROXIMITY_ALERT_ACTION_NAME = "ProximityAlert";
    private Button btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)findViewById(R.id.Button01);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set();
            }
        });
    }

    private void set() {
        String locService = Context.LOCATION_SERVICE;
        LocationManager locationManager;
        locationManager = (LocationManager)getSystemService(locService);
        double lat = 37.4;
        double lng = 55.0;
        float radius = 200f;
        long expiration = -1;
        Intent intent = new Intent(PROXIMITY_ALERT_ACTION_NAME);
        PendingIntent pi = PendingIntent.getBroadcast(this, -1, intent, 0);
        checkPermission("android.permission.ACCESS_FINE_LOCATION", 1, 1);
        locationManager.addProximityAlert(lat, lng, radius, expiration, pi);
    }
}
