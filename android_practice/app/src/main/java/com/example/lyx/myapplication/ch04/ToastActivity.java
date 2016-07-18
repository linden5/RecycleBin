package com.example.lyx.myapplication.ch04;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/15.
 */
public class ToastActivity extends Activity {
    private Button b1, b2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button)findViewById(R.id.Button01);
        b2 = (Button)findViewById(R.id.Button02);

        final int l = Toast.LENGTH_LONG;
        final int s = Toast.LENGTH_SHORT;

        final String s1 = "More time";
        final String s2 = "Less time";

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t1 = Toast.makeText(getApplicationContext(), s1, l);
                t1.show();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t2 = Toast.makeText(getApplicationContext(), s2, s);
                t2.show();
            }
        });
    }
}
