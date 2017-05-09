package com.example.lyx.myapplication.ch03;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/13.
 */
public class TestStringActivity extends Activity {
    private TextView myTextView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_string);
        myTextView = (TextView)findViewById(R.id.myTextView02);

        String str = getString(R.string.test_str2).toString();
        myTextView.setText(str);
    }
}
