package com.example.lyx.myapplication.ch06;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/17.
 */
public class ActionActivity1 extends Activity {
    public static final String MY_ACTION = "com.example.lyx.myapplication.ch06.MY_ACTION";
    private Button btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.Button01);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(MY_ACTION);
                startActivity(intent);
            }
        });
    }
}
