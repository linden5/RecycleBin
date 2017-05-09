package com.example.lyx.myapplication.ch03;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/14.
 */
public class TestLayoutActivity extends Activity {
    private TextView myTextView;
    private EditText myEditText;
    private Button myButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        myTextView = (TextView)findViewById(R.id.layoutTextView01);
        myEditText = (EditText)findViewById(R.id.EditText01);
        myButton =(Button)findViewById(R.id.layoutButton01);
    }
}
