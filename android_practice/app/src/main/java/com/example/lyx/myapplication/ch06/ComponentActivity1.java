package com.example.lyx.myapplication.ch06;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/17.
 */
public class ComponentActivity1 extends Activity {
    private Button btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.Button01);
        btn.setText("ComponentName");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComponentName cn = new ComponentName(ComponentActivity1.this,
                        "com.example.lyx.myapplication.ch06.ComponentActivity2");
                Intent intent = new Intent();
                intent.setComponent(cn);
                startActivity(intent);
            }
        });
    }
}
