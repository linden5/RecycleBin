package com.example.lyx.myapplication.ch14;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lyx.myapplication.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by lyx on 2016/7/24.
 */
public class GeoCodeActivity extends Activity {
    private TextView tv;
    private Button b1, b2;
    private LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String locService = Context.LOCATION_SERVICE;
        locationManager = (LocationManager)getSystemService(locService);
        tv = (TextView)findViewById(R.id.TextView01);

        b1 = (Button)findViewById(R.id.Button01);
        b2 = (Button)findViewById(R.id.Button02);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forward();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reverse();
            }
        });
    }
    private void forward() {
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        String address = "北京天安门";
        List<Address> locations = null;
        try {
            locations = gc.getFromLocationName(address, 10);
            if (locations.size() > 0) {
                Address a = locations.get(0);
                double lat = a.getLatitude();
                double lng = a.getLongitude();
                tv.setText(lat + ":" + lng);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
     }

    private void reverse() {
        double lng = 116.46;
        double lat = 39.92;
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gc.getFromLocation(lat, lng, 10);
            StringBuilder sb = new StringBuilder();
            if (addresses.size() > 0) {
                Address a = addresses.get(0);
                for (int i = 0; i < a.getMaxAddressLineIndex(); i++) {
                    sb.append(a.getAddressLine(i)).append("\n");
                    sb.append(a.getLocality()).append("\n");
                    sb.append(a.getPostalCode()).append("\n");
                    sb.append(a.getCountryName()).append("\n");
                }
                tv.setText(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
