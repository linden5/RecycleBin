package com.example.lyx.myapplication.ch14;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lyx.myapplication.R;

import java.util.List;

/**
 * Created by lyx on 2016/7/24.
 */
public class MapActivity extends Activity {
    private LocationManager locationManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locate();
    }

    private void locate() {
        TextView tv = (TextView)findViewById(R.id.TextView01);
        StringBuilder builder = new StringBuilder("Available providers:");
        List<String> providers = locationManager.getProviders(true);
        LocationListener ll = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        int permission = checkPermission("android.permission.ACCESS_FINE_LOCATION", 1, 1);
        for (String provider : providers) {
            locationManager.requestLocationUpdates(provider, 0, 1000, ll);
            builder.append("\n").append(provider).append(":");
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                builder.append("(").append(lat).append(",").append(lon).append(")");
            } else {
                builder.append("No location info");
            }
        }
        tv.setText(builder);
    }
}
