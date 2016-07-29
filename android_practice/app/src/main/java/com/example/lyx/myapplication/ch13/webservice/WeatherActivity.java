package com.example.lyx.myapplication.ch13.webservice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lyx.myapplication.R;

import java.util.List;

/**
 * Created by lyx on 2016/7/23.
 */
public class WeatherActivity extends Activity {
    private TextView displayTextView;
    private Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_forecast);
        displayTextView = (TextView)findViewById(R.id.displayTextView03);
        spinner = (Spinner)findViewById(R.id.citySpinner01);

//        List<String> citys = WebServiceUtil.getCityList();
//        ArrayAdapter a = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, citys);
//        spinner.setAdapter(a);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String msg = WebServiceUtil.getWeatherMsgByCity(spinner.getSelectedItem().toString());
//                displayTextView.setText(msg);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }
}
