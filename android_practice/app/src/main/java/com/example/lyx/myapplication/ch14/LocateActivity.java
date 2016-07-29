package com.example.lyx.myapplication.ch14;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lyx.myapplication.R;

import org.w3c.dom.Text;

/**
 * Created by lyx on 2016/7/24.
 */
public class LocateActivity extends Activity {
    private LocationManager locationManager;
    private TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        tv = (TextView)findViewById(R.id.TextView01);

        checkPermission("android.permission.ACCESS_FINE_LOCATION", 1, 1);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        printMsg(location);
    }

    private void printMsg(Location l) {
        StringBuilder builder = new StringBuilder("Available Provider:");
        if (l != null) {
            double lat = l.getLatitude();
            double lon = l.getLongitude();
            builder.append("(").append(lat).append(",").append(lon).append(")");

            if(l.hasAccuracy()) {
                builder.append("\nAccuracy:");
                builder.append(l.getAccuracy());
            }

            if (l.hasBearing()) {
                builder.append("\nBearing:");
                builder.append(l.getBearing());
            }

            if (l.hasAltitude()) {
                builder.append("\nAltitude:");
                builder.append(l.getAltitude());
            }

            if (l.hasSpeed()) {
                builder.append("\nSpeed:");
                builder.append(l.getSpeed());
            }
        } else {
            builder.append("No location info");
        }
        tv.setText(builder);
    }
}
