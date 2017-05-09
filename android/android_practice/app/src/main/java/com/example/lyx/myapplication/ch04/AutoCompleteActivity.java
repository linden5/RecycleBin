package com.example.lyx.myapplication.ch04;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/16.
 */
public class AutoCompleteActivity extends Activity {
    private AutoCompleteTextView atv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autocomplete);
        atv = (AutoCompleteTextView)findViewById(R.id.AutoCompleteTextView01);

        String[] strs = {"abc", "abcd", "bcd", "bcde"};
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, strs);
        atv.setAdapter(adapter);
    }
}
