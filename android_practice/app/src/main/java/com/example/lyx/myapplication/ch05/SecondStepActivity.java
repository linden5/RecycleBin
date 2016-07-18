package com.example.lyx.myapplication.ch05;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lyx.myapplication.R;

/**
 * Created by lyx on 2016/7/17.
 */
public class SecondStepActivity extends Activity {
    private Button b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ch05_two);

        b2 = (Button)findViewById(R.id.Button02);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                SecondStepActivity.this.setResult(0, intent);
                SecondStepActivity.this.finish();
            }
        });
    }
}
